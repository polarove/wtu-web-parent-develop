package cn.neorae.wtu.module.netty.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class SSLConfiguration {

    public SslContext loadConfig(String env) throws IOException {
        ClassPathResource key;
        ClassPathResource pem;
        if (env.equals("dev")){
            pem = new ClassPathResource("ssl/dev/cert.pem");
            key = new ClassPathResource("ssl/dev/cert-key.pem");
        }
       else if (env.equals("pro")){
            pem = new ClassPathResource("ssl/pro/netty.pem");
            key = new ClassPathResource("ssl/pro/netty.key");
        } else{
           throw new RuntimeException("Netty -> SSL证书文件路径配置错误");
        }
        return SslContextBuilder.forServer(pem.getInputStream(), key.getInputStream()).build();
    }

}
