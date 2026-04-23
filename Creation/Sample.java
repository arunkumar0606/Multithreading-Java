package Creation;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

// 1. Thread class
public class Sample extends Thread{
    @Override
    public void run(){
        System.out.println("Thread started by extending thread class..");
    }

    public static void main(String[] args) {
        Sample s = new Sample();
        s.start();
    }
}

// 2. Runnable interface
class Sample1 implements Runnable{

    @Override
    public void run() {
        System.out.println("Thread started by implementing runnable interface"+Thread.currentThread().getName());
    }

    public static void main(String[] args) {

        Thread t= new Thread(new Sample1());
        t.start();
    }
}
// 3. Anonymous
class Anon{
    public static void main(String[] args) {
        Thread t =new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Created using anonymous class ...");
            }
        });
        t.start();
    }
}
// 4. Lambda
class Lambda{
    public static void main(String[] args) {
        Thread t= new Thread( () -> System.out.println("Thread started using lambda.."));
        t.setName("Ak-Thread");
        t.start();
    }
}

// 5. Callable
class Square implements Callable<Integer> {

    private int a;

    public Square(int a ){
        this.a=a;
    }
    @Override
    public Integer call() {
        System.out.println("Finding square root of "+a);
        return a*a;
    }

    public static void main(String[] args) throws Exception {
        Integer ans= new Square(5).call();
        System.out.println(ans);
    }
}