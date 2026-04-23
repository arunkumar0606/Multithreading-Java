package CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/*
Methods
supplyAsync
thenApply
thenAccept
runAsync
thenCombine
 */
public class Sample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture.supplyAsync(()->"Arun")
                        .thenApply(x->x+" kumar")
                        .thenAccept(System.out::println);

        CompletableFuture.runAsync(()->{
            System.out.println("Running .. .");
        });

        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "A");
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> "Boo");

        CompletableFuture<String> result =
                f1.thenCombine(f2,(a,b)-> b+a);

        System.out.println(result.get()); // BooA
    }
}

class Sample1{
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync( ()->{
            try {
                System.out.println("cf1"+Thread.currentThread().getName());
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "hi";});

        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync( ()->{
            try {
                System.out.println("cf2"+Thread.currentThread().getName());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "arun";});

        CompletableFuture<String> ans =cf1.thenCombine(cf2,(a,b)-> a+":"+b);
        System.out.println(ans.join());
        System.out.println(CompletableFuture.anyOf(cf1, cf2).join());

        }



}
