package cn.unminded.bee.manage.controller;

import cn.unminded.bee.common.Result;
import cn.unminded.bee.common.annotation.Log;
import cn.unminded.bee.common.constant.DictStatusEnum;
import cn.unminded.bee.common.exception.BeeException;
import cn.unminded.bee.manage.dto.dict.request.ModifyDictRequest;
import cn.unminded.bee.manage.dto.dict.response.QueryDictItemResponse;
import cn.unminded.bee.persistence.criteria.QueryDictCriteria;
import cn.unminded.bee.persistence.entity.DictEntity;
import cn.unminded.bee.service.DictService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lijunwei
 */
@Log("字典配置")
@Slf4j
@RequestMapping("/manage/dict")
@RestController
public class DictController {

    @Resource
    private DictService dictService;

    @GetMapping("/list")
    public Result queryList(@RequestParam(value = "dictId", required = false) Long dictId,
                            @RequestParam(value = "typeCode", required = false) String typeCode,
                            @RequestParam(value = "itemCode", required = false) String itemCode,
                            @RequestParam(value = "itemName", required = false) String itemName,
                            @RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        QueryDictCriteria criteria = new QueryDictCriteria()
                .setDictId(dictId)
                .setTypeCode(typeCode)
                .setItemCode(itemCode)
                .setItemName(itemName)
                .setStart(Objects.equals(page, 1) ? 0 : (page - 1) * limit)
                .setLimit(limit);
        List<DictEntity> list = dictService.query(criteria);
        Map<String, Object> data = new HashMap<>();
        data.put("items", list);
        data.put("total", dictService.count(criteria));
        return Result.ok(data);
    }

    @PostMapping("/create")
    public Result create(@Validated @RequestBody DictEntity dictEntity) {
        dictService.save(dictEntity);
        return Result.ok(dictEntity.getDictId());
    }

    @PostMapping("/modify")
    public Result modify(@Validated @RequestBody ModifyDictRequest request) {
        if (Objects.isNull(request.getDictId())
                && StringUtils.isBlank(request.getItemCode())
                && StringUtils.isBlank(request.getItemName())) {
            throw new BeeException("dictId itemCode itemName 不能同时为null");
        }
        if (StringUtils.isBlank(request.getTypeCode())
                && StringUtils.isBlank(request.getItemValue())
                && StringUtils.isBlank(request.getItemDesc())
                && Objects.isNull(request.getItemStatus())
                && Objects.isNull(request.getSort())) {
            throw new BeeException("typeCode itemValue itemDesc itemStatus sort 不能同时为null");
        }
        DictEntity dictEntity = new DictEntity()
                .setDictId(request.getDictId())
                .setTypeCode(request.getTypeCode())
                .setItemCode(request.getItemCode())
                .setItemName(request.getItemName())
                .setItemValue(request.getItemValue())
                .setItemDesc(request.getItemDesc())
                .setItemStatus(request.getItemStatus())
                .setSort(request.getSort())
                .setUpdateTime(LocalDateTime.now());
        dictService.update(dictEntity);
        return Result.ok();
    }

    @GetMapping("/status")
    public Result dictStatus() {
        return Result.ok(DictStatusEnum.toMapList());
    }

    @GetMapping("/query")
    public Result itemList(@RequestParam("itemName") String itemName) {
        QueryDictCriteria criteria = new QueryDictCriteria();
        criteria.setItemName(itemName);
        criteria.setItemStatus(DictStatusEnum.RUNNING.getStatus());
        List<DictEntity> query = dictService.query(criteria);
        List<QueryDictItemResponse> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(query)) {
            result = query.stream()
                    .map(x -> new QueryDictItemResponse()
                        .setItemCode(x.getItemCode())
                        .setItemValue(x.getItemValue())
                    ).collect(Collectors.toList());
        }
        return Result.ok(result);
    }

}