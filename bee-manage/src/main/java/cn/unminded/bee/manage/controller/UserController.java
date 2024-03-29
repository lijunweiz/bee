package cn.unminded.bee.manage.controller;


import cn.unminded.bee.common.util.IdGenerator;
import cn.unminded.bee.common.Result;
import cn.unminded.bee.manage.dto.user.UserInfo;
import cn.unminded.bee.manage.constant.ManageLoginEnum;
import cn.unminded.bee.manage.constant.UserRoleEnum;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lijunwei
 */
@Slf4j
@RequestMapping("/manage/user")
@RestController
public class UserController {

    @PostMapping("/login")
    public Result userLogin() {
        Map<String, Object> data = new HashMap<>();
        data.put("token", IdGenerator.uuid());
        return Result.ok(ManageLoginEnum.LOGIN_SUCCESS.getCode(), ManageLoginEnum.LOGIN_SUCCESS.getDesc(), data);
    }

    @GetMapping("/info")
    public Result userInfo(@RequestParam("token") String token) {

        UserInfo data = UserInfo.builder().name("admin").roles(Arrays.asList(UserRoleEnum.ADMIN.getRole())).build();

        return Result.ok(ManageLoginEnum.LOGIN_SUCCESS.getCode(), ManageLoginEnum.LOGIN_SUCCESS.getDesc(), data);
    }

    @PostMapping("/logout")
    public Result userLogout(@RequestHeader("X-Token") String token) {
        log.info("X-Token: {}", token);
        return Result.ok();
    }

}
