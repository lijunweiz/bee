package cn.unminded.bee.manage.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRoleEnum {

    ADMIN("admin"),
    DEVELOP("developer"),
    EDITOR("editor"),
    GUEST("guest"),
    ;

    private final String role;

}
