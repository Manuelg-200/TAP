package Es4;
import Es1.RWbasic;

public class RWext extends RWbasic {
    private int toRead = 0;
    
    @Override
    public synchronized int read() {
        while(toRead == 0) {
            try {
                wait();
            } catch (Exception e) {
                System.out.println("Error in wait: " + e.getMessage());
                e.printStackTrace();
            }
        }
        toRead--;
        return super.read();
    }

    @Override
    public synchronized void write() {
        super.write();
        toRead++;
        try { 
            notify();
        } catch (Exception e) {
            System.out.println("Error in notify: " + e.getMessage());
            e.printStackTrace();
        }
    }
}