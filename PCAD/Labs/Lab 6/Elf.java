import java.util.Random;

public class Elf implements Runnable {
    private static Random rand = new Random();
    private static final Boolean isReindeer = false;
    private SantaLab santaLab;
    private SantaBed santaBed;

    public Elf(SantaLab santaLab, SantaBed santaBed) {
        this.santaLab = santaLab;
        this.santaBed = santaBed;
    }

    public void run() {
        while (true) {
            // Elf is building toys
            try {
                System.out.println(Thread.currentThread().getName() + " is building toys");
                Thread.sleep(rand.nextInt(10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Elf has a problem and needs Santa's help
            System.out.println(Thread.currentThread().getName() + " has a problem and needs Santa's help");
            santaLab.knock();
            if(santaLab.Enter())
                santaBed.wakeUp(isReindeer);   
            santaLab.AskForHelp();
            santaLab.Exit();             
        }
    }
}
