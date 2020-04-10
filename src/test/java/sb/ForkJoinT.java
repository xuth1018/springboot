package sb;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class ForkJoinT {
    // 定义最小区间为10
    private final static int MAX_THRESHOLD = 10;

    public static void main(String[] args) { final ForkJoinPool forkJoinPool = new ForkJoinPool();
        Integer future = forkJoinPool.invoke(new CalculateRecursiveTask(1, 100));
            System.out.println(future);
    }

    private static class  CalculateRecursiveTask extends RecursiveTask<Integer>{
        // 起始
        private int start;
        // 结束
        private int end;

        public CalculateRecursiveTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if(end-start<=MAX_THRESHOLD){
                return IntStream.rangeClosed(start,end).sum();
            }else{
                int middle = (start+end)/2;
                CalculateRecursiveTask taskLeft = new CalculateRecursiveTask(start,middle);
                CalculateRecursiveTask taskRight = new CalculateRecursiveTask(middle+1,end);
                //执行子任务
                taskLeft.fork();
                taskRight.fork();
                return taskLeft.join()+taskRight.join();
            }
        }
    }
}
