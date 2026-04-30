package Collections;

import java.util.concurrent.*;

public class MultiList {

    public static void main(String[] args) throws InterruptedException {

        CopyOnWriteArrayList<String> users = new CopyOnWriteArrayList<>();

        users.add("Arun");
        users.add("John");
        users.add("David");

        // Reader thread (iterates safely)
        Runnable reader = () -> {
            for (String user : users) {
                System.out.println(Thread.currentThread().getName() + " reading: " + user);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        // Writer thread (modifies list)
        Runnable writer = () -> {
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println("Adding new user...");
            users.add("Kumar");

            System.out.println("Removing user...");
            users.remove("John");
        };

        Thread t1 = new Thread(reader, "Reader-1");
        Thread t2 = new Thread(reader, "Reader-2");
        Thread t3 = new Thread(writer, "Writer");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Final List: " + users);
    }
}