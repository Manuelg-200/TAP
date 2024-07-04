package Es2;

import java.lang.Thread;

public class Writer implements Runnable {
    private RWexclusive rw;

    public Writer(RWexclusive rw) {
        this.rw = rw;
    }
    
    // Lo sleep non serve più
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is writing");
        rw.write();
    }
}
