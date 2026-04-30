package Collections;


import java.util.concurrent.*;
import java.util.*;

/*
User merge() and compute()

 */
public class HashMap {

    public static void main(String[] args) throws InterruptedException {

        ConcurrentHashMap<String, Integer> userCountMap = new ConcurrentHashMap<>();

        ExecutorService pool = Executors.newFixedThreadPool(4);

        List<String> users = Arrays.asList(
                "arun", "john", "arun", "david", "john",
                "arun", "david", "john", "arun", "john"
        );

        for (String user : users) {
            pool.submit(() -> {
                // Thread-safe update
                //userCountMap.merge(user, 1, Integer::sum);
                System.out.println("using thread : " + Thread.currentThread().getName());
                userCountMap.put(user, userCountMap.getOrDefault(user, 0) + 1);
            });
        }

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println(userCountMap);
    }
}

