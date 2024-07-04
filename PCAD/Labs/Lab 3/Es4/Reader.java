package Es4;

public class Reader implements Runnable {
    private RWext rw;

    public Reader(RWext rw) {
        this.rw = rw;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is reading: " + rw.read());
    }
}
