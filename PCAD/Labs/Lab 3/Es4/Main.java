package Es4;
import java.lang.Thread;

public class Main {

    public static void main(String[] args) {
        RWext rw = new RWext();
        try {
            Thread[] readers = new Thread[50];
            Thread[] writers = new Thread[50];
            
            for(int i=0; i < 50; i++) {
                writers[i] = new Thread(new Writer(rw), "Writer-" + i);
                readers[i] = new Thread(new Reader(rw), "Reader-" + i);
            }

            for(int i=0; i < writers.length; i++) {
                writers[i].start();
                readers[i].start();
            }
            for(int i=0; i < writers.length; i++) {
                writers[i].join();
                readers[i].join();
            }
            
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
