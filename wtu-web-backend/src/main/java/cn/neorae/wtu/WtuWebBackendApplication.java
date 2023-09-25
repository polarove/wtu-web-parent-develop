package cn.neorae.wtu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("cn.neorae.wtu.module.*.mapper")
@EnableAsync
public class WtuWebBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WtuWebBackendApplication.class, args);
    }

}
