package Synchronizers;

import java.util.concurrent.*;

/*
countDown() -> reduces count
await() -> wait for count 0
 */
public class CountDownLach {

    public static void main(String[] args) throws InterruptedException {

        int numberOfServices = 3;

        CountDownLatch latch = new CountDownLatch(numberOfServices);

        ExecutorService pool = Executors.newFixedThreadPool(3);

        // Service 1
        pool.execute(() -> {
            try {
                callService("ADB", 4);
            } finally {
                latch.countDown();
                System.out.println("Latch count : " +latch.getCount());
            }
        });

        // Service 2
        pool.execute(() -> {
            try {
                callService("CIS", 5);
            } finally {
                latch.countDown();
                System.out.println("Latch count : " +latch.getCount());
            }
        });

        // Service 3
        pool.execute(() -> {
            try {
                callService("CustomerAPI", 1);
            } finally {
                latch.countDown();
                System.out.println("Latch count : " +latch.getCount());
            }
        });

        // Wait for all services
        latch.await();

        System.out.println("All services completed. Proceeding further...");
        System.out.println("Other code executing . . .");

        pool.shutdown();
    }

    static void callService(String name, int seconds) {
        try {
            System.out.println(name + " started");
            Thread.sleep(seconds * 1000);
            System.out.println(name + " completed");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
