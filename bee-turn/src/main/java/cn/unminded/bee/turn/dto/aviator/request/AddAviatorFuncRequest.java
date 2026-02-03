package cn.unminded.bee.turn.dto.aviator.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author lijunwei
 */
@Accessors(chain = true)
@Data
public class AddAviatorFuncRequest {
    /** 英文名, 必须与com.googlecode.aviator.runtime.type.AviatorFunction#getName()函数返回名称一致  */
    @NotBlank(message = "英文名不能为空")
    private String funcNameEn;
    /** 中文名 */
    @NotBlank(message = "中文名不能为空")
    private String funcNameZh;
    /** 1-系统内置,2-自定义,3-第三方扩展 */
    private Integer funcType;
    /** 函数具体实现 只能有一个public class {@link cn.unminded.bee.core.util.JavaParserUtil#extractClassNameWithJavaParser(String)} */
    @NotBlank(message = "函数具体实现不能为空")
    private String func;
    /** 函数功能描述 */
    @NotBlank(message = "函数功能描述不能为空")
    private String funcDesc;

}
