package cn.unminded.bee.common.constant;

import org.apache.commons.lang3.StringUtils;

/**
 * @author lijunwei
 */
public class BeeConstant {

    private BeeConstant() {
        throw new UnsupportedOperationException();
    }

    public static final Integer OK = 200;
    public static final String OK_MSG = "处理成功";

    public static final Integer FAIL = 400;
    public static final String FAIL_MSG = "处理失败";

    public static final Integer ZERO = 0;

    public static final Integer YES = 1;

    public static final Integer NO = 0;

    public static final String EMPTY_STRING = StringUtils.EMPTY;

    /** bee开始开发时间 */
    public static final String BEE_START_TIME = "2024-01-01 00:00:00";

}
