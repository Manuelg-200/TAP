package Es3;

public class Reader implements Runnable {
    private RW rw;

    public Reader(RW rw) {
        this.rw = rw;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is reading: " + rw.read());
    }
}
