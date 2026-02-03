package cn.unminded.bee.core.constant;

import java.util.concurrent.TimeUnit;

/**
 * @author lijunwei
 */
public class BeeConstant {

    private BeeConstant() {
        throw new UnsupportedOperationException();
    }

    public static final String GET = "GET";

    public static final String POST = "POST";

    public static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

    public static final long DEFAULT_TIMEOUT = 5;

    public static final String DEFAULT_FUNC_PACKAGE = "package cn.unminded.bee.core.func;";


}
