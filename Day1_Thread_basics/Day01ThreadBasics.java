public class Day01ThreadBasics{

    public static void main(String[] args) throws InterruptedException{

        Runnable task = () -> {
            String name = Thread.currentThread().getName();
            for(int i=1;i<=5;i++){
                System.out.println(name + "-> step " + i);
                sleepSilently(60);
            }
        };

        Thread worker1 = new Thread(task,"worker-1");
        Thread worker2 = new Thread(task,"worker-2");

        System.out.println("main: starting Threads");

        worker1.start();
        worker2.start();

        worker1.join();
        worker2.join();

        System.out.println("main: done (both workers finished)");
    }

    private static void sleepSilently(long ms){
        try{
            Thread.sleep(ms);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}