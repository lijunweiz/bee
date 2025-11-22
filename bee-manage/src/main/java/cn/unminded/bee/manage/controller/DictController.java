package cn.unminded.bee.manage.controller;

import cn.unminded.bee.common.Result;
import cn.unminded.bee.common.annotation.Log;
import cn.unminded.bee.common.constant.DictStatusEnum;
import cn.unminded.bee.common.exception.BeeException;
import cn.unminded.bee.manage.dto.dict.request.ModifyDictRequest;
import cn.unminded.bee.persistence.criteria.QueryDictCriteria;
import cn.unminded.bee.persistence.entity.DictEntity;
import cn.unminded.bee.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public Result queryList(@RequestParam(value = "dictCode", required = false) String dictCode,
                            @RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        QueryDictCriteria criteria = new QueryDictCriteria()
                .setDictCode(dictCode)
                .setStart(Objects.equals(page, 1) ? 0 : (page - 1) * limit)
                .setLimit(limit);
        List<DictEntity> list = dictService.query(criteria);
        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("total", dictService.count(criteria));
        return Result.ok(data);
    }

    @PostMapping("/create")
    public Result create(@Validated @RequestBody DictEntity dictEntity) {
        dictService.save(dictEntity);
        return Result.ok(dictEntity.getId());
    }

    @PostMapping("/modify")
    public Result modify(@Validated @RequestBody ModifyDictRequest request) {
        if (Objects.isNull(request.getId())
                && StringUtils.isBlank(request.getDictCode())) {
            throw new BeeException("id dictCode 不能同时为空");
        }

        DictEntity dictEntity = new DictEntity()
                .setId(request.getId())
                .setDictCode(request.getDictCode())
                .setDictValue(request.getDictValue())
                .setStatus(request.getStatus())
                .setDescription(request.getDescription())
                .setUpdatedTime(LocalDateTime.now());
        dictService.update(dictEntity);
        return Result.ok();
    }

    @GetMapping("/status")
    public Result dictStatus() {
        return Result.ok(DictStatusEnum.toMapList());
    }

}