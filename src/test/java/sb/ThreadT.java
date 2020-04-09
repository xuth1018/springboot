package sb;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池策略
 * CallerRunsPolicy 多余的main执行
 * AbortPolicy 抛弃 抛异常
 * DiscardOldestPolicy 丢弃最早的任务 加入新任务
 * DiscardPolicy  丢弃新任务 不抛异常
 *
 * IO密集型任务：IO密集型任务线程并不是一直在执行任务，应该配置尽可能多的线程，
 * 线程池线程数量推荐设置为2 * CPU核心数；对弈IO密集型任务，网络上也有另一种线程池数量
 * 计算公式：CPU核心数/(1 - 阻塞系数)，阻塞系数取值0.8~0.9，至于这两种公式使用哪一个，可以根据实际环境测试比较得出；
 *
 * 计算密集型任务：此类型需要CPU的大量运算，所以尽可能的去压榨CPU资源，
 * 线程池线程数量推荐设置为CPU核心数 + 1。
 * Runtime.getRuntime().availableProcessors()
 */
public class ThreadT {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2, 3, 10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), Thread::new,
                new ThreadPoolExecutor.DiscardOldestPolicy());

        threadPoolExecutor.execute(new shortTask("任务1"));
        threadPoolExecutor.execute(new longTask("任务2"));
        threadPoolExecutor.execute(new longTask("任务3"));
        threadPoolExecutor.execute(new shortTask("任务4"));
        threadPoolExecutor.execute(new shortTask("任务5"));
        threadPoolExecutor.execute(new longTask("任务6"));
        threadPoolExecutor.execute(new shortTask("任务7"));

        threadPoolExecutor.shutdown();
    }

    static class shortTask implements Runnable {

        private String name;

        public shortTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + "执行shortTask-name-" + name + "完毕");
            } catch (InterruptedException e) {
                System.err.println("shortTask执行过程中被打断" + e.getMessage());
            }
        }
    }

    static class longTask implements Runnable {

        private String name;

        public longTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "执行longTask-name-" + name + "完毕");
            } catch (InterruptedException e) {
                System.err.println("longTask执行过程中被打断" + e.getMessage());
            }
        }
    }
}
