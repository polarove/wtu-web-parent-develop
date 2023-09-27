package cn.neorae.wtu;

import cn.neorae.wtu.module.netty.NettyApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("cn.neorae.wtu.module.*.mapper")
@EnableAsync
public class WtuWebBackendApplication {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(WtuWebBackendApplication.class, args);
        NettyApplication.start(3344, "/en", 4433, "/cn");
    }

}
