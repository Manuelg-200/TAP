import java.util.Random;

public class Reindeer implements Runnable {
    private static Random rand = new Random();
    private static final Boolean isReindeer = true;
    private HeatedStable heatedStable;
    private SantaBed SantaBed;
    private Sleigh sleigh;

    public Reindeer(HeatedStable heatedStable, SantaBed SantaBed, Sleigh sleigh) {
        this.heatedStable = heatedStable;
        this.SantaBed = SantaBed;
        this.sleigh = sleigh;
    }

    public void run() {
        while (true) {
            // Reindeer is on vacation
            try {
                System.out.println(Thread.currentThread().getName() + " is on vacation");
                Thread.sleep(rand.nextInt(10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Reindeer is back from the vacation, the last one goes to wake up Santa
            if (heatedStable.waitForTheOthers())
                SantaBed.wakeUp(isReindeer);

            // Reindeer is attached to the sleigh, and goes to deliver presents with Santa
            sleigh.attachReindeer();
        }
    }
}
