package cn.neorae.wtu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import cn.neorae.wtu.module.netty.module.team.TeamNettyServer;

@SpringBootApplication
@MapperScan("cn.neorae.wtu.module.*.mapper")
@EnableAsync
public class WtuWebBackendApplication {

    public static void main(String[] args) throws InterruptedException, Exception{
        SpringApplication.run(WtuWebBackendApplication.class, args);
        TeamNettyServer.start();
    }

}
