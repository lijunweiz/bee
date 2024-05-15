package cn.unminded.bee.common;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

import java.time.LocalDateTime;

public class Result {

    public static final Integer OK = 1;
    public static final Integer FAIL = 0;
    public static final String OK_MSG = "处理成功";
    public static final String FAIL_MSG = "处理失败";

    private String timestamp;

    private Integer code;

    private String desc;

    private Object data;

    public Result() {
        this.timestamp = DateUtil.format(LocalDateTime.now(), DatePattern.NORM_DATETIME_MS_PATTERN);
    }

    public Result(Integer code, String desc, Object data) {
        this.code = code;
        this.desc = desc;
        this.timestamp = DateUtil.format(LocalDateTime.now(), DatePattern.NORM_DATETIME_MS_PATTERN);
        this.data = data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Result setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public Result setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public Result setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "Result{" +
                "timestamp='" + timestamp + '\'' +
                ", code=" + code +
                ", desc='" + desc + '\'' +
                ", data=" + data +
                '}';
    }

    public static Result ok() {
        return new Result(OK, OK_MSG, null);
    }

    public static Result ok(Integer code) {
        return new Result(code, OK_MSG, null);
    }

    public static Result ok(String desc) {
        return new Result(OK, desc, null);
    }

    public static Result ok(Object data) {
        return new Result(OK, OK_MSG, data);
    }

    public static Result ok(Integer code, String desc) {
        return new Result(code, desc, null);
    }

    public static Result ok(Integer code, String desc, Object data) {
        return new Result(code, desc, data);
    }

    public static Result fail() {
        return new Result(FAIL, FAIL_MSG, null);
    }

    public static Result fail(Integer code) {
        return new Result(code, FAIL_MSG, null);
    }

    public static Result fail(String desc) {
        return new Result(FAIL, desc, null);
    }

    public static Result fail(Object data) {
        return new Result(FAIL, FAIL_MSG, data);
    }

    public static Result fail(Integer code, String desc) {
        return new Result(code, desc, null);
    }

    public static Result fail(Integer code, String desc, Object data) {
        return new Result(code, desc, data);
    }

}
