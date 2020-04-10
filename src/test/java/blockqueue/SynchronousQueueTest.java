package blockqueue;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import java.util.stream.IntStream;

/**
 *  SynchronousQueue是这样一种阻塞队列，其中每个 put 必须等待一个 take，
 *  反之亦然。同步队列没有任何内部容量，甚至连一个队列的容量都没有。
 *  
 *   iterator() 永远返回空，因为里面没东西。 
 * peek() 永远返回null。 
 * put() 往queue放进去一个element以后就一直wait直到有其他thread进来把这个element取走。 
 * offer() 往queue里放一个element后立即返回，如果碰巧这个element被另一个thread取走了，offer方法返回true，认为offer成功；否则返回false。 
 * offer(2000, TimeUnit.SECONDS) 往queue里放一个element但是等待指定的时间后才返回，返回的逻辑和offer()方法一样。 
 * take() 取出并且remove掉queue里的element（认为是在queue里的。。。），取不到东西他会一直等。 
 * poll() 取出并且remove掉queue里的element（认为是在queue里的。。。），只有到碰巧另外一个线程正在往queue里offer数据或者put数据的时候，该方法才会取到东西。否则立即返回null。 
 * poll(2000, TimeUnit.SECONDS) 等待指定的时间然后取出并且remove掉queue里的element,其实就是再等其他的thread来往里塞。 
 * isEmpty()永远是true。 
 * remainingCapacity() 永远是0。 
 * remove()和removeAll() 永远是false。 
 */
public class SynchronousQueueTest {
    public static void main(String[] args) {
        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();
        IntStream.range(1,2).forEach(i->new Thread(new Product(synchronousQueue),"生产线程"+i).start());
        IntStream.range(4,5).forEach(i->new Thread(new Consumer(synchronousQueue),"消费线程"+i).start());

    }

    static class Product extends Thread{
        SynchronousQueue<Integer> synchronousQueue;
        Product(SynchronousQueue<Integer> synchronousQueue){
            this.synchronousQueue = synchronousQueue;
        }

        @Override
        public void run() {
            while(true){
                int rand = new Random().nextInt(1000);
                System.out.println("生产了一个产品："+rand);
                System.out.println("等待三秒后运送出去...");
                try {
                    synchronousQueue.put(rand);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(synchronousQueue.isEmpty());
           }
        }
    }

    static class Consumer extends Thread{
        SynchronousQueue<Integer> synchronousQueue;
        Consumer(SynchronousQueue<Integer> synchronousQueue){
            this.synchronousQueue = synchronousQueue;
        }

        @Override
        public void run() {
           // while(true){
                try {
                    System.out.println("消费了一个产品"+synchronousQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("==========================================");
          //  }
        }
    }
}
