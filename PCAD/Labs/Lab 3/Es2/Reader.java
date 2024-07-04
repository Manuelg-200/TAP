package Es2;

public class Reader implements Runnable {
    private RWexclusive rw;

    public Reader(RWexclusive rw) {
        this.rw = rw;
    }

    @Override
    public void run() {
        System.out.println(rw.read());
    }
}
