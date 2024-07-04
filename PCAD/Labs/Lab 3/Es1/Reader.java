package Es1;

public class Reader implements Runnable {
    private RWbasic rw;

    public Reader(RWbasic rw) {
        this.rw = rw;
    }

    @Override
    public void run() {
        System.out.println(rw.read());
    }
}
