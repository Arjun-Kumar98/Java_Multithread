class MyThread extends Thread {
    public void run() {
        System.out.println("Thread is running..."+Thread.currentThread().getName());
    }
}

public class Day1Thread{

    public static void main(String[] args) {
        System.out.println("Main thread is running..."+Thread.currentThread().getName());
        MyThread t1 = new MyThread();
        t1.start();
       // t1.run();
        System.out.println("End of main");
}
}