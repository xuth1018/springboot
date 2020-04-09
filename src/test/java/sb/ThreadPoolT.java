package sb;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolT {
    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(
                5,10,30,
                TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(),Thread::new,
                new ThreadPoolExecutor.AbortPolicy());
        System.out.println("创建线程池");
        int activeCount = -1;
        int queueSize = -1;
        System.out.println("线程池活跃："+((ThreadPoolExecutor) executorService).getActiveCount());
        System.out.println("线程池核心："+((ThreadPoolExecutor) executorService).getCorePoolSize());
        System.out.println("线程池："+((ThreadPoolExecutor) executorService).getMaximumPoolSize());
        System.out.println("线程池："+((ThreadPoolExecutor) executorService).getLargestPoolSize());
        System.out.println("线程池："+((ThreadPoolExecutor) executorService).getQueue().size());

        ExecutorService executorService1 = Executors.newFixedThreadPool(10);
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

}

