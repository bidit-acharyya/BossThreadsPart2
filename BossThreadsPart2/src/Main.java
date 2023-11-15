import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Main
{
    static AtomicInteger total = new AtomicInteger(0);
    static long startTime = System.currentTimeMillis();
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        ArrayList<Future<Integer>> futures = new ArrayList<>();

        for(int i = 0; i < 100; i++)
        {
            Future<Integer> future = executorService.submit((Callable<Integer>) () -> {
                AtomicInteger localSum = new AtomicInteger(0);
                for(int j = 0; j < 1000000; j++)
                {
                    localSum.getAndAdd(1);
                }
                return localSum.get();
            });
            futures.add(future);
        }

        for(Future future : futures)
        {
            total.getAndAdd((Integer) future.get());
        }
        System.out.println(System.currentTimeMillis() - startTime);
        System.out.println(total.get());



    }
}
