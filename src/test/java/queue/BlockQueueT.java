package queue;

import com.sun.nio.zipfs.ZipInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.SimpleFormatter;

public class BlockQueueT {

    BlockingQueue<String> queue = new ArrayBlockingQueue<String>(5);

    public static void main(String[] args) {
        ZipInfo info = new ZipInfo();
        System.out.println(info.getClass().getClassLoader());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(2131);
        System.out.println(new SimpleDateFormat("mm:ss").format(calendar.getTime()));
        System.out.println(2131/60+":"+2131%60);
    }
}
