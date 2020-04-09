package sb;

import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ReentrantLockT {
    public static final ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        IntStream.range(1,4).forEach(i->new Thread(ReentrantLockT::locks,"线程"+i).start());
    }

    public static void locks(){
        System.out.println(Thread.currentThread().getName()+"开始");
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"锁住");
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName()+"释放");
        }
    }
}
