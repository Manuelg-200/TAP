public class HeatedStable {
    private int reindeersInside = 0;

    // For the reindeers, return true if all the reindeers are inside
    public synchronized Boolean waitForTheOthers() {
        reindeersInside++;
        System.out.println(Thread.currentThread().getName() + " is waiting for the others");
        try {
            if(reindeersInside < 9) {
                wait();    
                return false;   
            }      
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (reindeersInside == 9);
    }

    // For Santa
    public synchronized void leadReindeers() {
        System.out.println("Santa is getting the reindeers from the stables");
        reindeersInside = 0;
        notifyAll();
    }
}    

