package cn.unminded.bee.manage.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ManageLoginEnum {

    LOGIN_SUCCESS(100000, "登录成功"),
    ILLEGAL_TOKEN(100001, "Token无效"),
    OTHER_CLIENT_LOGIN(100002, "另一个客户端登录"),
    TOKEN_EXPIRED(100002, "Token已过期"),

    ;

    private final Integer code;

    private final String desc;

}
