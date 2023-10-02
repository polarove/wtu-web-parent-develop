package cn.neorae.wtu;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class WtuWebBackendApplicationTests {

    @Test
    void contextLoads() {
    }

}
