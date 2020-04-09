package queue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *  信号量控制同时访问特定资源的线程数量
 *  构造函数指定信号量 线程可以调用acquire获取许可证
 *  调用release归还许可证
 */
public class SemaphoreTest {

    public static void main(String[] args) throws InterruptedException {
        final MySemaphore semaphore = new MySemaphore(1);
        IntStream.range(0,4).forEach(i-> new Thread(()->{
            String name = Thread.currentThread().getName();
            System.out.println(Thread.currentThread().getName()+"开始");
            try{
                semaphore.acquire();
                System.out.println(name+"获取许可证");
                TimeUnit.SECONDS.sleep(3);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                System.out.println(name+"释放许可证");
                semaphore.release();
            }
            System.out.println(Thread.currentThread().getName()+"结束");
        },"thread"+(i+1)).start()

        );
        while(true){
            if(semaphore.hasQueuedThreads()){
                System.out.println("等待线程的数量："+semaphore.getQueueLength());
                Collection<Thread> c = semaphore.getQueuedThreads();
                System.out.println("等待的线程："+c.stream().map(Thread::getName).collect(Collectors.joining(",")));
                Thread.sleep(3000);
            }

        }

    }


    static class MySemaphore extends Semaphore {
        private static final long serialVersionUID = -2595494765642942297L;

        public MySemaphore(int permits) {
            super(permits);
        }

        public MySemaphore(int permits, boolean fair) {
            super(permits, fair);
        }

        public Collection<Thread> getQueuedThreads() {
            return super.getQueuedThreads();
        }
    }
}
