package Creation;

//Thread class
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

//Runnable interface
class Sample1 implements Runnable{

    @Override
    public void run() {
        System.out.println("Thread started by implementing runnable interface");
    }

    public static void main(String[] args) {

        Thread t= new Thread(new Sample1());
        t.start();
    }
}

//Lambda
class Lambda{
    public static void main(String[] args) {
        Thread t= new Thread( () -> System.out.println("Thread started using lambda.."));
        t.start();
    }
}