package cn.unminded.bee.manage.controller;

import cn.unminded.bee.common.Result;
import cn.unminded.bee.common.annotation.Log;
import cn.unminded.bee.manage.constant.ModelStatusEnum;
import cn.unminded.bee.manage.constant.ModelTreeNodeTypeEnum;
import cn.unminded.bee.manage.dto.model.request.ModelTreeItemRequest;
import cn.unminded.bee.manage.dto.model.response.ModelTreeDataResponse;
import cn.unminded.bee.persistence.criteria.QueryModelCriteria;
import cn.unminded.bee.persistence.entity.ModelTreeEntity;
import cn.unminded.bee.service.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lijunwei
 */
@Log("模型")
@Slf4j
@RequestMapping("/manage/model")
@RestController
public class ModelController {

    @Resource
    private ModelService modelService;

    @GetMapping("/data")
    public Result getModelTreeData() {
        Map<String, Object> result = new HashMap<>();
        List<ModelTreeDataResponse> treeDataResult = new ArrayList<>();
        // 查询组
        QueryModelCriteria groupCriteria = new QueryModelCriteria().setIsLeaf(ModelTreeNodeTypeEnum.NO.getCode());
        List<ModelTreeEntity> list = modelService.modelTreeData(groupCriteria);
        for (ModelTreeEntity entity : list) {
            // 查询组成员
            QueryModelCriteria memberCriteria = new QueryModelCriteria()
                    .setModelType(entity.getModelType())
                    .setIsLeaf(ModelTreeNodeTypeEnum.YES.getCode());
            List<ModelTreeEntity> modelList = modelService.modelTreeData(memberCriteria);
            // 转换
            ModelTreeDataResponse item = new ModelTreeDataResponse();
            item.setModelId(entity.getModelId());
            item.setLabel(entity.getModelType());
            item.setIsLeaf(ModelTreeNodeTypeEnum.NO.getCode());
            if (CollectionUtils.isNotEmpty(modelList)) {
                List<ModelTreeDataResponse> treeDataResponses = modelList.stream().map(x -> {
                    ModelTreeDataResponse children = new ModelTreeDataResponse();
                    children.setModelId(x.getModelId());
                    children.setLabel(x.getModelName());
                    children.setIsLeaf(ModelTreeNodeTypeEnum.YES.getCode());
                    return children;
                }).collect(Collectors.toList());
                item.setTreeData(treeDataResponses);
            }

            treeDataResult.add(item);
        }
        result.put("treeData", treeDataResult);// 树形列表
        QueryModelCriteria itemCriteria = new QueryModelCriteria().setIsLeaf(ModelTreeNodeTypeEnum.YES.getCode());
        result.put("list", modelService.modelTreeData(itemCriteria));// 表格数据

        return Result.ok(result);
    }

    @PostMapping("/create/tree/item")
    public Result createTreeItem(@Validated @RequestBody ModelTreeItemRequest request) {
        if (Objects.equals(request.getIsLeaf(), ModelTreeNodeTypeEnum.YES.getCode())
                && StringUtils.isBlank(request.getModelName())) {
            return Result.fail("模型名称不能为空");
        }
        QueryModelCriteria criteria = new QueryModelCriteria()
                .setIsLeaf(request.getIsLeaf())
                .setModelType(request.getModelType())
                .setModelName(request.getModelName());
        List<ModelTreeEntity> list = modelService.modelTreeData(criteria);
        if (CollectionUtils.isNotEmpty(list)) {
            return Result.fail("已有相同的节点, 节点Id: " + list.get(0).getModelId());
        }

        ModelTreeEntity modelTreeEntity = new ModelTreeEntity()
                .setModelType(request.getModelType())
                .setModelName(request.getModelName())
                .setIsLeaf(request.getIsLeaf())
                .setModelDesc(request.getModelDesc())
                .setModelStatus(ModelStatusEnum.CREATE.getCode())
                .setOperator(request.getOperator())
                .setCreatedTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
        modelService.save(modelTreeEntity);

        return Result.ok(modelTreeEntity.getModelId());
    }
}
