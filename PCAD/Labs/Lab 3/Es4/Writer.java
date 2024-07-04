package Es4;

import java.lang.Thread;

public class Writer implements Runnable {
    private RWext rw;

    public Writer(RWext rw) {
        this.rw = rw;
    }
    
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is writing");
        rw.write();
    }
}
