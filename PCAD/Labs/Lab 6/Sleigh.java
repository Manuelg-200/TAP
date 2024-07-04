public class Sleigh {
    private static Boolean deliveredPresents = false;
    private static int attachedReindeers = 0;

    // For the reindeers
    public synchronized void attachReindeer() {
        attachedReindeers++;
        System.out.println(Thread.currentThread().getName() + " is getting attached to the sleigh");
        try {
            if(attachedReindeers == 9)
                notifyAll();
            while(!deliveredPresents)
                wait();
            attachedReindeers--;
            if(attachedReindeers == 0)
                deliveredPresents = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // For Santa
    public synchronized void deliverPresents() {
        try {
            // Wait for all the reindeers to be attached
            while(attachedReindeers < 9)
                wait();
            System.out.println("Santa is delivering presents");
            Thread.sleep(6000);
            deliveredPresents = true;
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }        
}
