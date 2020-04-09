package queue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {

    private BlockingQueue<String> queue;
    private final static int SLEEPING_TIME = 1000;
    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("启动消费者线程！");
        Random ran = new Random();
        boolean run = true;
        try {
            while (run) {
                String data = queue.poll(2, TimeUnit.SECONDS);
                if(null != data){
                    System.out.println("消费者取出数据data："+data);
                    System.out.println();
                    Thread.sleep(ran.nextInt(SLEEPING_TIME));
                }else{
                    run = false;
                }
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }finally {
            System.out.println("退出消费者线程");
        }

    }

}
