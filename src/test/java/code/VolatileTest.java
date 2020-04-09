package code;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class VolatileTest {
    private volatile static int INIT_VALUE = 0;
    private final static int LIMIT = 5;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            int value = INIT_VALUE;
            while (value < LIMIT) {
                if (value != INIT_VALUE) {
                    System.out.println("获取更新后的值：" + INIT_VALUE);
                    value = INIT_VALUE;
                }
            }
        }, "reader");
        Thread t2 =  new Thread(() -> {
            int value = INIT_VALUE;
            while (INIT_VALUE < LIMIT) {
                System.out.println("将值更新为：" + ++value);
                INIT_VALUE = value;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "writer");
        t1.start();
        t2.start();

        AtomicInteger i = new AtomicInteger(0);
        boolean result = i.compareAndSet(0,1);
        System.out.println(i.get());

        AtomicReference<String> reference = new AtomicReference<>();

        //解决ABA
        AtomicStampedReference<String> stampedReference = new AtomicStampedReference<>("A",1);
    }
}