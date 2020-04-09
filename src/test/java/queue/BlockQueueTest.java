package queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockQueueTest {


    public static void main(String[] args) throws InterruptedException {
        int num = 1;
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(10);

        Consumer consumer1 = new Consumer(queue);
        Consumer consumer2 = new Consumer(queue);
        Consumer consumer3 = new Consumer(queue);
        Producer producer1 = new Producer(queue);

        for(int i =0;i<10;i++){

        }
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(producer1);
        service.execute(consumer2);
        service.execute(consumer3);
        service.execute(consumer1);
        Thread.sleep(5000);
        producer1.stop();
        Thread.sleep(2000);
        service.shutdown();
    }


}
