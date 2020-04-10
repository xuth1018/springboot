package queue;

import com.sun.nio.zipfs.ZipInfo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockQueueT {

    BlockingQueue<String> queue = new ArrayBlockingQueue<String>(5);

    public static void main(String[] args) {
        ZipInfo info = new ZipInfo();
        System.out.println(info.getClass().getClassLoader());
    }
}
