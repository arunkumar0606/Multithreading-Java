package ExecutorService;

import java.util.List;
import java.util.concurrent.*;

/*
Methods
invokeAny()
invokeAll()
 */
public class Part2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService pool= Executors.newFixedThreadPool(1);
        List<Callable<Integer>> ls = List.of(
                ()-> {System.out.println("executing 1");return 1;},
                ()->  {System.out.println("executing 2");return 2;},
                ()->  {System.out.println("executing 3");return 3;}
        );
        int ans = pool.invokeAny(ls);
        List<Future<Integer>> ansLst =pool.invokeAll(ls);
        pool.shutdown();
        System.out.println(ans);
        System.out.println(ansLst);
    }
}
//awaitTermination
class Part3{
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(1);

        pool.submit(() -> {
            try {
                System.out.println("running...");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        pool.shutdown();

        if (pool.awaitTermination(1, TimeUnit.SECONDS)) {
            System.out.println("Pool closed");
            pool.shutdownNow();
        } else {
            System.out.println("Pool not closed");
        }
    }
}
