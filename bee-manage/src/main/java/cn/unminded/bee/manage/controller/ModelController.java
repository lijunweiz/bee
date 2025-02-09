package cn.unminded.bee.manage.controller;

import cn.unminded.bee.common.Result;
import cn.unminded.bee.common.annotation.Log;
import cn.unminded.bee.common.constant.ModelStatusEnum;
import cn.unminded.bee.common.constant.ModelTreeNodeTypeEnum;
import cn.unminded.bee.manage.dto.model.request.DeleteModelTreeItemRequest;
import cn.unminded.bee.manage.dto.model.request.ModelTreeItemRequest;
import cn.unminded.bee.manage.dto.model.response.ModelTreeDataResponse;
import cn.unminded.bee.persistence.criteria.DeleteModelCriteria;
import cn.unminded.bee.persistence.criteria.QueryModelCriteria;
import cn.unminded.bee.persistence.entity.ModelTreeEntity;
import cn.unminded.bee.service.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
    public Result getModelTreeData(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        Map<String, Object> data = new HashMap<>();
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
            item.setModelDesc(entity.getModelDesc());
            if (CollectionUtils.isNotEmpty(modelList)) {
                List<ModelTreeDataResponse> treeDataResponses = modelList.stream().map(x -> {
                    ModelTreeDataResponse children = new ModelTreeDataResponse();
                    children.setModelId(x.getModelId());
                    children.setLabel(x.getModelName());
                    children.setIsLeaf(ModelTreeNodeTypeEnum.YES.getCode());
                    children.setModelDesc(x.getModelDesc());
                    return children;
                }).collect(Collectors.toList());
                item.setTreeData(treeDataResponses);
            }

            treeDataResult.add(item);
        }
        data.put("treeData", treeDataResult);// 树形列表
        QueryModelCriteria itemCriteria = new QueryModelCriteria()
                .setStart(Objects.equals(page, 1) ? 0 : (page - 1) * limit)
                .setLimit(limit)
                .setIsLeaf(ModelTreeNodeTypeEnum.YES.getCode());
        data.put("list", modelService.modelTreeData(itemCriteria));// 表格数据
        data.put("total", modelService.count(itemCriteria));
        return Result.ok(data);
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

    @PostMapping("/update/tree/item")
    public Result updateTree(@Validated @RequestBody ModelTreeItemRequest request) {
        if (Objects.equals(request.getIsLeaf(), ModelTreeNodeTypeEnum.YES.getCode())
                && StringUtils.isBlank(request.getModelName())) {
            return Result.fail("模型名称不能为空");
        }
        if (Objects.isNull(request.getModelId())) {
            return Result.fail("modelId不能为null");
        }

        ModelTreeEntity modelTreeEntity = new ModelTreeEntity();
        BeanUtils.copyProperties(request, modelTreeEntity);
        modelService.update(modelTreeEntity);

        return Result.ok();
    }

    @PostMapping("/delete/tree/item")
    public Result delete(@Validated @RequestBody DeleteModelTreeItemRequest request) {
        DeleteModelCriteria criteria = new DeleteModelCriteria()
                .setModelId(request.getModelId())
                .setIsLeaf(request.getIsLeaf());
        modelService.delete(criteria);
        return Result.ok();
    }
}
