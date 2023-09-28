package cn.neorae.wtu;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("cn.neorae.wtu.module.*.mapper")
@EnableAsync
@Slf4j
public class WtuWebBackendApplication {

    public static void main(String[] args){

        SpringApplication.run(WtuWebBackendApplication.class, args);
    }

}
