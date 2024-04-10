package cn.unminded.bee.core.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author lijunwei
 */
class JSONPathParserTest {

    @Test
    void testParseNoFilters() {
        String json = "{\n" +
                "  \"widget\": {\n" +
                "    \"debug\": \"on\",\n" +
                "    \"window\": {\n" +
                "      \"title\": \"Client Info\",\n" +
                "      \"name\": \"client_info\",\n" +
                "      \"width\": 500,\n" +
                "      \"height\": 500,\n" +
                "      \"padding\": [10,10,10,50],\n" +
                "      \"locations\" : [\n" +
                "        { \"name\": \"header\", \"display\": \"true\" },\n" +
                "        { \"name\": \"footer\", \"display\": \"true\" },\n" +
                "        { \"name\": \"sidebar\", \"display\": \"false\" }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}";

        String jsonPath = "$.widget.window.title";

        try {
            String parse = JSONPathParser.parse(json, jsonPath);
            System.out.println(parse);
            Assertions.assertNotNull(parse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
