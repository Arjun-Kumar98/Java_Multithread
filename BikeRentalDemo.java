public class BikeRentalDemo {
    
    static class BikeInventory{
        private int availableBikes = 7;

        public synchronized void rentBike(String userName){
            if(availableBikes > 0){
                System.out.println(userName+" renting a bike.");

                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

               availableBikes--;
               System.out.println("Bikes left after "+userName+" are: "+availableBikes);
            } else {
                System.out.println(userName+" tried to rent a bike, but none are available.");
            }
        }
    }

    public static void main(String[] args){
        BikeInventory inventory = new BikeInventory();

      for(int i=1;i<=10;i++){
        String userName = "User-" + i;

        Thread thread = new Thread(()->{
            inventory.rentBike(userName);
        });
        thread.start();
      }
    }
}
