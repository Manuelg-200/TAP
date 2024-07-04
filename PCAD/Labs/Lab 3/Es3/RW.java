package Es3;
import Es1.RWbasic;

public class RW extends RWbasic {
    private int readerCount = 0;

    @Override
    public synchronized int read() {
        readerCount++;
        var result = super.read();
        readerCount--;
        try {
            if(readerCount == 0) 
                notifyAll();
        } catch (Exception e) {
            System.out.println("Error in notifyAll: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public synchronized void write() {
        try {
            if(readerCount > 0) 
                wait();
        } catch (Exception e) {
            System.out.println("Error in wait: " + e.getMessage());
            e.printStackTrace();
        }
        super.write();
    }
}
