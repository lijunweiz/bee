
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;

import java.util.HashMap;
import java.util.Map;

/**
 * 产品编码转换，自定义函数新增、测试
 * @author lijunwei
 */
public class ProductCodeConvert extends AbstractFunction {

    private Map<String, String> productCodeMap = new HashMap<>();

    public ProductCodeConvert() {
        productCodeMap.put("acc", "ACC");
        productCodeMap.put("bbq", "BBQ");
    }

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1) {
        String arg1String = FunctionUtils.getStringValue(arg1, env);
        String productCode = productCodeMap.get(arg1String);
        return new AviatorString(productCode);
    }

    @Override
    public String getName() {
        return "bee.productCodeConvert";
    }

}
