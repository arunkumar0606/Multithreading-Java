package ExecutorService;


import javax.print.attribute.standard.RequestingUserName;
import java.time.LocalTime;
import java.util.concurrent.*;

/*
Main methods
1) execute() -> runnable
2) submit() -> runnable, callable
 */
public class Sample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Runnable r = ()-> System.out.println("Task 1 ..");
        executor.execute(r);   // execute

        Callable<Integer> c = ()-> 100;
        Future<Integer> ans = executor.submit(c);   // callable

        int val=0;

        try{
            val=ans.get();
        }catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(val);

        executor.shutdown();
    }
}

/*
 newSingleThreadExecutor-> one at a time
 */
class Variation2{
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        for(int i=0;i<6;i++){
            pool.execute( ()-> {
                        {
                            System.out.println("Thread" + Thread.currentThread().getName());
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
            );
        }
    }
}

/*
FixedThreadPool -> fixed at a time
 */
class Variation{
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        for(int i=0;i<12;i++){
            pool.execute( ()-> {
                System.out.println("Thread"+ Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}

/*
newCachedThreadPool - unlimited threads
 */
class Variation3{
    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        for(int i=0;i<12;i++){
            pool.execute( ()-> {
                System.out.println("Thread"+ Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}

/*
newScheduledThreadPool -> schedule future execution
 */
class Variation4{
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //3 methods
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(3);

        Callable<Integer> c = ()-> 1000;

        Runnable r1 = ()-> {
            System.out.println("Executed 1.schedule at "+ LocalTime.now());
        };
        Runnable r2 = ()-> {
            System.out.println("Executed 2.scheduleAtFixedRate at "+ LocalTime.now());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        Runnable r3 = ()-> {
            System.out.println("Executed 2.scheduleWithFixedDelay at "+ LocalTime.now());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };


        // 1 schedule -> run once in future
        pool.schedule(r1,2,TimeUnit.SECONDS);

        // 2 scheduleAtFixedRate -> runs each interval
        pool.scheduleAtFixedRate(r2,1,2,TimeUnit.SECONDS);

        // 3 scheduleWithFixedDelay -> runs after completing previous
        pool.scheduleWithFixedDelay(r3,1,2,TimeUnit.SECONDS);

        Future<Integer> ans= pool.schedule(c,1,TimeUnit.SECONDS);
        System.out.println(ans.get());

    }
}
