package cn.unminded.bee.manage.controller;

import cn.unminded.bee.common.Result;
import cn.unminded.bee.common.annotation.Log;
import cn.unminded.bee.common.constant.DictStatusEnum;
import cn.unminded.bee.common.exception.BeeException;
import cn.unminded.bee.manage.dto.dict.request.ModifyDictItemRequest;
import cn.unminded.bee.manage.dto.dict.response.QueryDictItemResponse;
import cn.unminded.bee.persistence.criteria.QueryDictItemCriteria;
import cn.unminded.bee.persistence.entity.DictItemEntity;
import cn.unminded.bee.service.DictItemService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author lijunwei
 */
@Log("字典项配置")
@Slf4j
@RequestMapping("/manage/dict/item")
@RestController
public class DictItemController {

    @Resource
    private DictItemService dictItemService;

    @GetMapping("/list")
    public Result queryListItem(@RequestParam(value = "dictCode", required = false) String dictCode,
                                @RequestParam(value = "itemCode", required = false) String itemCode,
                                @RequestParam(value = "itemName", required = false) String itemName,
                                @RequestParam(value = "page", defaultValue = "1") Integer page,
                                @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        QueryDictItemCriteria criteria = new QueryDictItemCriteria()
                .setDictCode(dictCode)
                .setItemCode(itemCode)
                .setItemName(itemName)
                .setStart(Objects.equals(page, 1) ? 0 : (page - 1) * limit)
                .setLimit(limit);
        List<DictItemEntity> list = dictItemService.query(criteria);
        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("total", dictItemService.count(criteria));
        return Result.ok(data);
    }


    @PostMapping("/create")
    public Result create(@Validated @RequestBody DictItemEntity dictItemEntity) {
        dictItemService.save(dictItemEntity);
        return Result.ok(dictItemEntity.getId());
    }

    @PostMapping("/modify")
    public Result modify(@Validated @RequestBody ModifyDictItemRequest request) {
        if (Objects.isNull(request.getDictCode())
                && StringUtils.isBlank(request.getItemCode())
                && StringUtils.isBlank(request.getItemName())) {
            throw new BeeException("dictCode itemCode itemName 不能同时为null");
        }
        DictItemEntity dictItemEntity = new DictItemEntity()
                .setId(request.getId())
                .setItemName(request.getItemName())
                .setItemValue(request.getItemValue())
                .setStatus(request.getStatus())
                .setSort(request.getSort())
                .setDescription(request.getDescription())
                .setUpdatedTime(LocalDateTime.now());
        dictItemService.update(dictItemEntity);
        return Result.ok();
    }

    @GetMapping("/status")
    public Result dictStatus() {
        return Result.ok(DictStatusEnum.toMapList());
    }

    @GetMapping("/query")
    public Result itemList(@RequestParam("dictCode") String dictCode) {
        QueryDictItemCriteria criteria = new QueryDictItemCriteria();
        criteria.setDictCode(dictCode);
        criteria.setStatus(DictStatusEnum.RUNNING.getStatus());
        List<DictItemEntity> query = dictItemService.query(criteria);
        List<QueryDictItemResponse> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(query)) {
            result = query.stream()
                    .map(x -> new QueryDictItemResponse()
                            .setItemName(x.getItemName())
                            .setItemValue(x.getItemValue())
                    ).collect(Collectors.toList());
        }
        return Result.ok(result);
    }

}