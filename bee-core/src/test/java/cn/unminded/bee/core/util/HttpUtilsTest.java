package cn.unminded.bee.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @author lijunwei
 */
class HttpUtilsTest {


    @Test
    void testGet() throws IOException {
        String s = HttpUtils.get("https://www.12306.cn/?a=b", Map.of("k1", "1"));
        System.out.println(s);
        Assertions.assertTrue(Objects.nonNull(s));
    }

    @Test
    void testPost() throws IOException {
        String s = HttpUtils.post("https://www.12306.cn/?a=b", Map.of("p1", "1"), Map.of("h1", ""), Map.of("b1", 1));
        System.out.println(s);
        Assertions.assertTrue(Objects.nonNull(s));
    }

}
