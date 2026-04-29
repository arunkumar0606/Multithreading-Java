package Synchronizers;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;
import java.util.concurrent.*;

class Service implements Runnable {

    private String name;
    private CyclicBarrier barrier;

    Service(String name, CyclicBarrier barrier) {
        this.name = name;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            // Phase 1
            System.out.println(name + " performing Phase 1");
            Thread.sleep((int) (Math.random()  * 3000));

            System.out.println(name + " waiting at barrier");
            barrier.await(); // wait for others

            // Phase 2 (only after all reach barrier)
            System.out.println(name + " starting Phase 2");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


public class CyclicBareer {

    public static void main(String[] args) {

        int numberOfServices = 3;

        CyclicBarrier barrier = new CyclicBarrier(
                numberOfServices,
                () -> System.out.println("All services reached barrier. Starting next phase...")
        );

        ExecutorService pool = Executors.newFixedThreadPool(numberOfServices);

        pool.submit(new Service("ADB", barrier));
        pool.submit(new Service("CIS", barrier));
        pool.submit(new Service("CustomerAPI", barrier));

        pool.shutdown();
    }
}