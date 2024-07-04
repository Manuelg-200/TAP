public class SantaBed {
    private Boolean isSantaSleeping = false;
    private Boolean ReindeersAreBack = false;
    private Boolean ElvesNeedHelp = false;

    // For Santa, returns who woke him up
    public synchronized WhoIsIt sleep() {
        System.out.println("Santa is sleeping");
        try {
            isSantaSleeping = true;
            wait();
            isSantaSleeping = false;
            System.out.println("Santa is waking up");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(ReindeersAreBack && ElvesNeedHelp) {
            ReindeersAreBack = false;
            ElvesNeedHelp = false;
            return WhoIsIt.BOTH;
        } else if(ReindeersAreBack) {
            ReindeersAreBack = false;
            return WhoIsIt.REINDEERS;
        } else {
            ElvesNeedHelp = false;
            return WhoIsIt.ELVES;
        }
    }

    // For elves or reindeers
    public synchronized void wakeUp(Boolean isReindeer) {
        try {
            while(!isSantaSleeping)
                wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " is waking up Santa");
        if(isReindeer)
            ReindeersAreBack = true;
        else
            ElvesNeedHelp = true;
        notify();
    }
}