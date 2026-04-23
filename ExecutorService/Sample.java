package ExecutorService;


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
