package Es1;

import java.lang.Thread;
import java.util.Random;

public class Writer implements Runnable {
    private RWbasic rw;
    private Random rand = new Random();

    public Writer(RWbasic rw) {
        this.rw = rw;
    }
    
    // Con sleep randomico, il valore data è corretto (con valore random abbastanza grande rispetto al numero di scrittori)
    // Con sleep fisso, o esecuzione immediata il valore di data cambia ogni esecuzione
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is writing");
        try {
            Thread.sleep(rand.nextInt(1000));
        } catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        rw.write();
    }
}
