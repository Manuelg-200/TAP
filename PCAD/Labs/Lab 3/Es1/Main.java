package Es1;
import java.lang.Thread;

public class Main {

    public static void main(String[] args) {
        RWbasic rw = new RWbasic();
        try {
            Thread reader = new Thread(new Reader(rw));
            Thread[] writers = new Thread[50];
            
            for(int i=0; i < 50; i++) 
                writers[i] = new Thread(new Writer(rw), "Writer-" + i);

            for(int i=0; i < writers.length; i++)
                writers[i].start();
            for(int i=0; i < writers.length; i++)
                writers[i].join();

            reader.start();
            reader.join();
            
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}