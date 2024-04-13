package cn.unminded.bee.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cn.unminded.bee.persistence")
@SpringBootApplication(scanBasePackages = "cn.unminded.bee")
public class BeeManage {

    public static void main(String[] args) {
        SpringApplication.run(BeeManage.class, args);
    }

}
