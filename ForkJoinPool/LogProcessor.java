package ForkJoinPool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class LogProcessor extends RecursiveTask<Integer> {

    private String[] logs;
    private int start, end;
    private static final int THRESHOLD = 3;

    LogProcessor(String[] logs, int start, int end) {
        this.logs = logs;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {

        // Base case (small chunk)
        if (end - start <= THRESHOLD) {
            int count = 0;
            for (int i = start; i < end; i++) {
                if (logs[i].contains("ERROR")) {
                    count++;
                }
            }
            return count;
        }

        // Divide
        int mid = (start + end) / 2;

        LogProcessor left = new LogProcessor(logs, start, mid);
        LogProcessor right = new LogProcessor(logs, mid, end);

        // Fork left (async)
        left.fork();

        // Compute right (sync)
        int rightResult = right.compute();

        // Join left
        int leftResult = left.join();

        return leftResult + rightResult;
    }
}

class LogProcessorAction extends RecursiveAction {

    private String[] logs;
    private int start, end;
    private AtomicInteger errorCount;
    private static final int THRESHOLD = 3;

    LogProcessorAction(String[] logs, int start, int end, AtomicInteger errorCount) {
        this.logs = logs;
        this.start = start;
        this.end = end;
        this.errorCount = errorCount;
    }

    @Override
    protected void compute() {

        // Base case
        if (end - start <= THRESHOLD) {
            for (int i = start; i < end; i++) {
                if (logs[i].contains("ERROR")) {
                    errorCount.incrementAndGet(); // thread-safe
                }
            }
            return;
        }

        // Divide
        int mid = (start + end) / 2;

        LogProcessorAction left = new LogProcessorAction(logs, start, mid, errorCount);
        LogProcessorAction right = new LogProcessorAction(logs, mid, end, errorCount);

        // Fork & Join
        left.fork();
        right.compute();
        left.join();
    }
}

 class Main {
    public static void main(String[] args) {

        String[] logs = {
                "INFO User logged in",
                "ERROR DB connection failed",
                "INFO Request processed",
                "ERROR Timeout occurred",
                "INFO Service started",
                "ERROR Null pointer"
        };

        ForkJoinPool pool = new ForkJoinPool();

        //RecursiveTask
        LogProcessor task = new LogProcessor(logs, 0, logs.length);
        int errorCount = pool.invoke(task);
        System.out.println("Total Errors using task: " + errorCount);

        //RecursiveAction
        AtomicInteger errorCount1 = new AtomicInteger(0);
        LogProcessorAction task1 =
                new LogProcessorAction(logs, 0, logs.length, errorCount1);
        pool.invoke(task1);
        System.out.println("Total Errors using action: " + errorCount1.get());

        pool.shutdown();
    }
}