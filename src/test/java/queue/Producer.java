package queue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    private BlockingQueue<String> queue;
    private final static int SLEEPING_TIME = 1000;
    private static boolean flag = true;

    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        Random ran = new Random();
        try {
            while (flag) {
                System.out.println("生产者" + Thread.currentThread().getName() + "开始放入数据===");
                queue.put(String.valueOf(ran.nextInt(SLEEPING_TIME)));
                            Thread.sleep(SLEEPING_TIME);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("退出生产者线程");

        }
    }

    public void stop() {
        flag = false;
    }
}
