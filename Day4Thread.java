
class Counter{

    int count = 0;
    static int staticCount = 0;

    public synchronized void increment(){
        count++;
    }

    public static synchronized void incrementStatic(){
        staticCount++;
    }
}


public class Day4Thread {
    
    public static void main(String[] args) throws InterruptedException{

        System.out.println("Case 1: Same Object");
    
        Counter counter1 = new Counter();

        Thread t1 = new Thread(()->{
            for(int i=0;i<100000;i++){
                counter1.increment();
            }
        });

        Thread t2 = new Thread(()->{
            for(int i=0;i<100000;i++){
                counter1.increment();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final count (same object): "+counter1.count);   

        System.out.println("\n Case 2: Different Objects");

        Counter c1 = new Counter();
        Counter c2 = new Counter();

        Thread t3 = new Thread(()->{
            for(int i=0;i<100000;i++){
                c1.increment();
            }
        });

        Thread t4 = new Thread(()->{
            for(int i=0;i<100000;i++){
                c2.increment();
            }
        });

        t3.start();
        t4.start();

        t3.join();
        t4.join();

        System.out.println("Final count c1: "+c1.count);
        System.out.println("Final count c2: "+c2.count);

        System.out.println("\n Case 3: Static Lock");

        Thread t5 = new Thread(()->{
            for(int i=0;i<100000;i++){
                Counter.incrementStatic();
            }
        });

        Thread t6 = new Thread(()->{
            for(int i=0;i<100000;i++){
                Counter.incrementStatic();
            }
        });

        t5.start();
        t6.start();

        t5.join();
        t6.join();

        System.out.println("Final static count: "+Counter.staticCount);

    }


}
