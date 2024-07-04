import java.util.*;

public class My_Queue {
    /* Versione limitata
    private static final int MAX_QUEUE_SIZE = 10;
    */

    private Queue<String> queue = new LinkedList<String>();

    public synchronized void Write(String message) {
        try {
            /* Versione limitata
            if(queue.size() == MAX_QUEUE_SIZE)
                wait();
            */
            queue.add(message + " " + queue.size());
            notify();
        } catch(Exception e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
    }

    public synchronized String Read() {
        try {
            if(queue.isEmpty())
                wait();
            String message = queue.poll();
            notify();
            return message;
        } catch(Exception e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
            return null;
        }
    }
}