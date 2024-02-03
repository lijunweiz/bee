package cn.unminded.bee.manage.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Builder
@Accessors(chain = true)
@Data
public class UserInfo {

    private String name;

    private String avatar;

    private List<String> roles;

}
