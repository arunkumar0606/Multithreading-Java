package Synchronizers;

import java.util.concurrent.*;

public class SemaphoreExample {

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(2); // allow 2 threads at a time

        ExecutorService pool = Executors.newFixedThreadPool(5);

        for (int i = 1; i <= 6; i++) {
            int requestId = i;

            pool.submit(() -> {
                try {
                    semaphore.acquire(); // request permission

                    System.out.println("Processing request " + requestId +
                            " by " + Thread.currentThread().getName());

                    Thread.sleep(2000); // simulate API call

                    System.out.println("Completed request " + requestId);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    semaphore.release(); // release permit
                }
            });
        }

        pool.shutdown();
    }
}