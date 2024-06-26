package cn.unminded.bee.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Result {

    public static final Integer OK_INT = 1;
    public static final Integer FAIL_INT = 0;
    public static final String OK_MSG = "处理成功";
    public static final String FAIL_MSG = "处理失败";
    private static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    private String timestamp;

    private Integer code;

    private String desc;

    private Object data;

    public Result() {
        this.timestamp = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_SSS).format(new Date());
    }

    public Result(Integer code, String desc, Object data) {
        this.code = code;
        this.desc = desc;
        this.timestamp = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_SSS).format(new Date());
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
        return new Result(OK_INT, OK_MSG, null);
    }

    public static Result ok(Integer code) {
        return new Result(code, OK_MSG, null);
    }

    public static Result ok(String desc) {
        return new Result(OK_INT, desc, null);
    }

    public static Result ok(Object data) {
        return new Result(OK_INT, OK_MSG, data);
    }

    public static Result ok(Integer code, String desc) {
        return new Result(code, desc, null);
    }

    public static Result ok(Integer code, String desc, Object data) {
        return new Result(code, desc, data);
    }

    public static Result fail() {
        return new Result(FAIL_INT, FAIL_MSG, null);
    }

    public static Result fail(Integer code) {
        return new Result(code, FAIL_MSG, null);
    }

    public static Result fail(String desc) {
        return new Result(FAIL_INT, desc, null);
    }

    public static Result fail(Object data) {
        return new Result(FAIL_INT, FAIL_MSG, data);
    }

    public static Result fail(Integer code, String desc) {
        return new Result(code, desc, null);
    }

    public static Result fail(Integer code, String desc, Object data) {
        return new Result(code, desc, data);
    }

}
