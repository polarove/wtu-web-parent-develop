package cn.neorae.wtu.common.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public class VisibleThreadPoolExecutor extends ThreadPoolTaskExecutor{

    private void displayThreadPoolInfo (String prefix) {
        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();

        log.info("{}, {},taskCount [{}], completedTaskCount [{}], activeCount [{}], queueSize [{}]",
                this.getThreadNamePrefix(),
                prefix,
                threadPoolExecutor.getTaskCount(),
                threadPoolExecutor.getCompletedTaskCount(),
                threadPoolExecutor.getActiveCount(),
                threadPoolExecutor.getQueue().size());
    }

    @Override
    public void execute(Runnable task) {
        displayThreadPoolInfo("1. do execute");
        super.execute(task);
    }

    @Override
    public Future<?> submit(Runnable task) {
        displayThreadPoolInfo("1. do submit");
        return super.submit(task);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        displayThreadPoolInfo("2. do submit");
        return super.submit(task);
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        displayThreadPoolInfo("2. do submitListenable");
        return super.submitListenable(task);
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        displayThreadPoolInfo("2. do submitListenable");
        return super.submitListenable(task);
    }
}
