package sb;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

/**
 * list set hashmap
 * 并发容器
 * copyonwritearraylist
 * CopyOnWriteArraySet
 * concurrenthashmap
 */
public class CollectionT {

    private static  List<Integer> list = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        IntStream.range(1,30).forEach(i->new Thread(()->{
            list.add(i);
        },"Thread"+i).start());
        Thread.sleep(1000);
        Collections.sort(list);
        System.out.println(list);
    }

}
