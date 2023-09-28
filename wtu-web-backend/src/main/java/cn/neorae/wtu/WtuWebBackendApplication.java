package cn.neorae.wtu;

import cn.neorae.wtu.module.netty.module.cn.CnChannelMap;
import cn.neorae.wtu.module.netty.module.en.EnChannelMap;
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
        log.info("初始化国际服频道列表");
        EnChannelMap.init();
        log.info("初始化国服频道列表");
        CnChannelMap.init();
    }

}
