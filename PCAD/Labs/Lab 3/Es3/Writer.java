package Es3;

import java.lang.Thread;

public class Writer implements Runnable {
    private RW rw;

    public Writer(RW rw) {
        this.rw = rw;
    }
    
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is writing");
        rw.write();
    }
}
