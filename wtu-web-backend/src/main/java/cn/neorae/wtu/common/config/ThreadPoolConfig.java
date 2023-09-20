package cn.neorae.wtu.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolConfig {

    /**
     * 核心线程数
     */
    private static final int CORE_POOL_SIZE = 8;

    /**
     * 最大线程数
     */
    private static final int MAX_POOL_SIZE = 16;

    /**
     * 队列大小
     */
    private static final int QUEUE_CAPACITY = 100;

    /**
     * 线程存活时间,单位秒
     */
    private static final int KEEP_ALIVE_SECONDS = 60;

    /**
     * 线程名前缀
     */
    private static final String THREAD_NAME_PREFIX = "ThreadPool-";

    /**
     * 线程池配置
     * @return ThreadPoolTaskExecutor 线程池
     */
     @Bean
     public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
         VisibleThreadPoolExecutor executor = new VisibleThreadPoolExecutor();
         executor.setCorePoolSize(CORE_POOL_SIZE);
         executor.setMaxPoolSize(MAX_POOL_SIZE);
         executor.setQueueCapacity(QUEUE_CAPACITY);
         executor.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
         executor.setThreadNamePrefix(THREAD_NAME_PREFIX);

         executor.setWaitForTasksToCompleteOnShutdown(true);
         executor.setAwaitTerminationSeconds(60);

         executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
         executor.initialize();
         return executor;
     }
}
