package com.segovelo.multithreading;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootTest
public class MultiThreadingApplicationTests {


    void startTasks(ThreadPoolTaskExecutor taskExecutor, CountDownLatch countDownLatch, int numThreads) {
        for (int i = 0; i < numThreads; i++) {
            taskExecutor.execute(() -> {
                try {
                    Thread.sleep(100L * ThreadLocalRandom.current().nextLong(1, 10));
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

    @Test
    public void whenUsingDefaults_thenSingleThread() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.afterPropertiesSet();

        CountDownLatch countDownLatch = new CountDownLatch(10);
        this.startTasks(taskExecutor, countDownLatch, 10);

        while (countDownLatch.getCount() > 0) {
            assertEquals(1, taskExecutor.getPoolSize());
        }
    }

    @Test
    public void whenCorePoolSizeFive_thenFiveThreads() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.afterPropertiesSet();

        CountDownLatch countDownLatch = new CountDownLatch(10);
        this.startTasks(taskExecutor, countDownLatch, 10);

        while (countDownLatch.getCount() > 0) {
            assertEquals(5, taskExecutor.getPoolSize());
        }
    }

    @Test
    public void whenCorePoolSizeFiveAndMaxPoolSizeTen_thenFiveThreads() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.afterPropertiesSet();

        CountDownLatch countDownLatch = new CountDownLatch(10);
        this.startTasks(taskExecutor, countDownLatch, 10);

        while (countDownLatch.getCount() > 0) {
            assertEquals(5, taskExecutor.getPoolSize());
        }
    }

    @Test
    public void whenCorePoolSizeFiveAndMaxPoolSizeTenAndQueueCapacityZero_thenTenThreads() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(0);
        taskExecutor.afterPropertiesSet();

        CountDownLatch countDownLatch = new CountDownLatch(10);
        this.startTasks(taskExecutor, countDownLatch, 10);

        while (countDownLatch.getCount() > 0) {
            assertEquals(10, taskExecutor.getPoolSize());
        }
    }

    @Test
    public void whenCorePoolSizeFiveAndMaxPoolSizeTenAndQueueCapacityTen_thenTenThreads() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        int corePool = 5;
        int maxPool = 10;
        int queueCap = 6;
        int numTasks = 4;
        taskExecutor.setCorePoolSize(corePool);
        taskExecutor.setMaxPoolSize(maxPool);
        taskExecutor.setQueueCapacity(queueCap);
        taskExecutor.afterPropertiesSet();
        CountDownLatch countDownLatch = new CountDownLatch(numTasks);
        this.startTasks(taskExecutor, countDownLatch, numTasks);       
        System.out.println("taskExecutor.setCorePoolSize("+String.valueOf(corePool)+")");
        System.out.println("taskExecutor.setMaxPoolSize("+String.valueOf(maxPool)+")");
        System.out.println("taskExecutor.setQueueCapacity("+String.valueOf(queueCap)+")");
        System.out.println("Number of Task: "+ numTasks);
        System.out.println("taskExecutor.getPoolSize() : " + taskExecutor.getPoolSize());
        while (countDownLatch.getCount() > 0) {
<<<<<<< HEAD
           assertEquals((numTasks - queueCap), taskExecutor.getPoolSize());
=======
        	int numThreads = 0;
        	if((numTasks - queueCap) > 0) {
        		if((numTasks - queueCap) >= maxPool)
        			numThreads = maxPool;
        		else if (numTasks - queueCap > corePool) 
        				numThreads = (numTasks - queueCap);
        					else numThreads = corePool;
        	}
        	else if (numTasks >= corePool) 
        			numThreads = corePool;
        				else numThreads = numTasks;
            Assert.assertEquals(numThreads, taskExecutor.getPoolSize());
>>>>>>> branch 'main' of https://github.com/segovelo/multi-threading.git
        }
    }

}
