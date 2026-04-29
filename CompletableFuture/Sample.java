package CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
Methods
supplyAsync
runAsync
thenApply
thenAccept
thenCombine
allOf
anyOf
thenCompose -> flatten completablefuture

uses ForkJoinPool.commonPool() internally
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

class Sample3{
    public static void main(String[] args) {

        ExecutorService pool = Executors.newFixedThreadPool(4);

        CompletableFuture<String> pipeline =
                CompletableFuture.supplyAsync(() -> fetchUserId(), pool)
                        .thenCompose(id -> CompletableFuture.supplyAsync(() -> fetchUser(id), pool))
                        .thenApply(user -> "Welcome, " + user.getName())
                        .exceptionally(ex -> "Error Guest")
                        .whenComplete((msg, ex) -> log("Done: " + msg));

        String result = pipeline.join();
        System.out.println(result);
    }

    static String fetchUserId(){
        return "#125";
    }
    static User fetchUser(String id){
        if(id.length()>4) {
            throw new NullPointerException();
        }
        return new User("arun");
    }
    static void log(String msg) {
        System.out.println(msg);
    }

    static class User{
        private String name;
        public User(String name){
            this.name=name;
        }
        public String getName(){
            return name;
        }
    }
}