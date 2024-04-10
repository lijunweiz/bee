package cn.unminded.bee.core.parser;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Predicate;

/**
 * @author lijunwei
 */
public class JSONPathParser {

    private JSONPathParser() {
        throw new UnsupportedOperationException();
    }

    public static <T> T parse(String json, String jsonPath) {
        return JsonPath.read(json, jsonPath);
    }

    public static <T> T parse(String json, String jsonPath, Predicate... filters) {
        return JsonPath.read(json, jsonPath, filters);
    }

}
