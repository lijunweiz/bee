package cn.unminded.bee.common.util;

import java.util.UUID;

public class IdGenerator {

    private IdGenerator() {
        throw new UnsupportedOperationException();
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
