package cn.neorae.wtu;

import cn.neorae.wtu.module.netty.module.cn.CnChannelMap;
import cn.neorae.wtu.module.netty.module.en.EnChannelMap;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("cn.neorae.wtu.module.*.mapper")
@EnableAsync
public class WtuWebBackendApplication {

    public static void main(String[] args){

        SpringApplication.run(WtuWebBackendApplication.class, args);
        EnChannelMap.init();
        CnChannelMap.init();
    }

}
