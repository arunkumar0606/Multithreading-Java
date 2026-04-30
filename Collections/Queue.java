package Collections;

import java.util.concurrent.*;

public class Queue {

    public static void main(String[] args) {

        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);

        ExecutorService pool = Executors.newFixedThreadPool(4);

        // Producer
        Runnable producer = () -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    String task = "Task-" + i;
                    System.out.println("Producing " + task);
                    queue.put(task); // waits if queue is full
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        // Consumer
        Runnable consumer = () -> {
            while (true) {
                try {
                    String task = queue.take(); // waits if queue is empty
                    System.out.println(Thread.currentThread().getName() +
                            " consumed " + task);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        };

        pool.submit(producer);
        pool.submit(consumer);
        pool.submit(consumer);

        pool.shutdown();
    }
}