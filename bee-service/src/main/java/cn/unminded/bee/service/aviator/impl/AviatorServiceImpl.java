package cn.unminded.bee.service.aviator.impl;

import cn.unminded.bee.common.constant.BeeConstant;
import cn.unminded.bee.core.RuleExecutor;
import cn.unminded.bee.core.constant.AviatorFunctionEnum;
import cn.unminded.bee.core.engine.AviatorRuleEngine;
import cn.unminded.bee.core.engine.compiler.DynamicCompiler;
import cn.unminded.bee.core.util.BeeCoreExceptionUtil;
import cn.unminded.bee.persistence.criteria.QueryFunctionCriteria;
import cn.unminded.bee.persistence.entity.FunctionEntity;
import cn.unminded.bee.persistence.mapper.FunctionMapper;
import cn.unminded.bee.service.aviator.AviatorService;
import cn.unminded.bee.turn.dto.aviator.request.AddAviatorFuncRequest;
import cn.unminded.bee.turn.dto.aviator.response.AviatorFuncResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import com.googlecode.aviator.runtime.type.AviatorFunction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static cn.unminded.bee.core.constant.BeeConstant.DEFAULT_FUNC_PACKAGE;

/**
 * @author lijunwei
 */
@Slf4j
@Service
public class AviatorServiceImpl implements AviatorService {

    private static final AviatorEvaluatorInstance AVIATOR_EVALUATOR_INSTANCE = AviatorRuleEngine.getInstance();

    @Resource
    private DynamicCompiler dynamicCompiler;

    @Resource
    private RuleExecutor ruleExecutor;

    @Resource
    private FunctionMapper functionMapper;

    /**
     * 自定义函数列表
     */
    private List<AviatorFuncResponse> aviatorFuncResponseList = Lists.newArrayList();

    @PostConstruct
    void loadFunFromDb() {
        this.aviatorFuncResponseList = this.loadFromDb();
    }

    @Override
    public Map<String, List<AviatorFuncResponse>> funMapList() {
        Map<String, List<AviatorFuncResponse>> funMapList = Maps.newHashMap();
        List<AviatorFuncResponse> sysFuncList = Lists.newArrayList();
        for (String name : AVIATOR_EVALUATOR_INSTANCE.getFuncMap().keySet()) {
            if (name.startsWith("bee.")) {
                // this.aviatorFuncResponseList
            } else {
                AviatorFuncResponse aviatorFuncResponse = new AviatorFuncResponse().setFuncType(BeeConstant.FUNCTION_TYPE1);
                AviatorFunctionEnum functionEnum = AviatorFunctionEnum.findByMethodName(name);
                if (Objects.nonNull(functionEnum)) {
                    aviatorFuncResponse.setFuncNameEn(functionEnum.getFunctionSignature());
                    aviatorFuncResponse.setFuncNameZh(functionEnum.getChineseName());
                    aviatorFuncResponse.setFuncDesc(functionEnum.getDescription());
                    sysFuncList.add(aviatorFuncResponse);
                }
            }
        }
        funMapList.put("sys", sysFuncList);
        funMapList.put("def", this.aviatorFuncResponseList);

        return funMapList;
    }

    /**
     * 加载已存在的函数定义
     * @return
     */
    private List<AviatorFuncResponse> loadFromDb() {
        List<AviatorFuncResponse> list = Lists.newArrayList();
        List<FunctionEntity> entityList = functionMapper.list(null);
        if (CollectionUtils.isEmpty(entityList)) {
            return Collections.emptyList();
        }
        for (FunctionEntity functionEntity : entityList) {
            try {
                this.compileAndInstantiate(functionEntity.getFunc());
            } catch (Exception e) {
                log.error("加载自定义函数失败: {}", functionEntity.getFuncNameEn(), e);
            }
            AviatorFuncResponse aviatorFuncResponse = new AviatorFuncResponse()
                    .setFuncNameEn(functionEntity.getFuncNameEn())
                    .setFuncNameZh(functionEntity.getFuncNameZh())
                    .setFuncType(functionEntity.getFuncType())
                    .setFuncDesc(functionEntity.getDescription())
                    ;
            list.add(aviatorFuncResponse);
        }

        return list;
    }

    @Override
    public void addFunc(AddAviatorFuncRequest func) {
        List<FunctionEntity> entityList = functionMapper.list(new QueryFunctionCriteria().setFuncNameEn(func.getFuncNameEn()));
        Integer version = 0;
        if (CollectionUtils.isNotEmpty(entityList)) {
            version = entityList.stream().map(FunctionEntity::getVersion).max(Integer::compareTo).orElse(0);
        }
        try {
            String funcSource = DEFAULT_FUNC_PACKAGE + "\n" + func.getFunc();
            AviatorFunction function = this.compileAndInstantiate(funcSource);
            BeeCoreExceptionUtil.trueToThrow(!StringUtils.equals(func.getFuncNameEn(), function.getName()),  "自定义函数名称不一致");
            FunctionEntity functionEntity = new FunctionEntity()
                    .setFuncNameEn(function.getName())
                    .setFuncNameZh(func.getFuncNameZh())
                    .setFuncType(BeeConstant.FUNCTION_TYPE2)
                    .setFunc(func.getFunc())
                    .setDescription(func.getFuncDesc())
                    .setVersion(version)
                    .setCreatedTime(LocalDateTime.now())
                    .setUpdatedTime(LocalDateTime.now())
                    ;
            functionMapper.insert(functionEntity);
            addFuncToList(functionEntity);
            log.info("新增自定义函数 {}: {}", func.getFuncNameEn(), func.getFuncNameZh());
        } catch (Exception e) {
            throw BeeCoreExceptionUtil.build(e);
        }
    }

    private void addFuncToList(FunctionEntity functionEntity) {
        AviatorFuncResponse aviatorFuncResponse = new AviatorFuncResponse()
                .setFuncNameEn(functionEntity.getFuncNameEn())
                .setFuncNameZh(functionEntity.getFuncNameZh())
                .setFuncType(functionEntity.getFuncType())
                .setFuncDesc(functionEntity.getDescription())
                ;
        this.aviatorFuncResponseList.add(aviatorFuncResponse);
    }

    private AviatorFunction compileAndInstantiate(String funcSource) throws Exception {
        AviatorFunction function = dynamicCompiler.compileAndInstantiate(
                funcSource,
                AviatorFunction.class);
        AVIATOR_EVALUATOR_INSTANCE.addFunction(function);

        return function;
    }

    @Override
    public void updateFunc(FunctionEntity func) {
        functionMapper.update(func);
    }

    @Override
    public Object eval(String expression, Map<String, Object> data) {
        return ruleExecutor.execute(expression, data);
    }

}
