package cn.unminded.bee.manage;


import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lijunwei
 */
class ConcurrentTest {

    @Test
    void testCompleteFuture() {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,1,1,
                TimeUnit.MINUTES, new ArrayBlockingQueue<>(1, true), new ThreadPoolExecutor.AbortPolicy());
        CompletableFuture.supplyAsync(()->{
            return null;
        }, executor);



        System.out.println("testCompleteFuture");

        Assert.isTrue(executor.getCorePoolSize() == 1, "OK");

    }

}
