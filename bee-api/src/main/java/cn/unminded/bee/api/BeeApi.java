package cn.unminded.bee.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cn.unminded.bee.persistence")
@SpringBootApplication(scanBasePackages = "cn.unminded.bee")
public class BeeApi {

    public static void main(String[] args) {
        SpringApplication.run(BeeApi.class, args);
    }

}
