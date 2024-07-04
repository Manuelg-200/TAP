import java.io.*;
import java.net.*;

public class TCP_Producer implements Runnable {
    private static final String GREET_MESSAGE = "producer";
    private static final String CONFIRM_MESSAGE = "okprod";
    private static final String PRODUCE_MESSAGE = "producer producing things";

    @Override
    public void run() {
        try {
            Socket socket = new Socket("localhost", 4242);
            BufferedReader receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter sender = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            sender.println(GREET_MESSAGE);
            sender.flush();
            String message = receiver.readLine();
            System.out.println(Thread.currentThread().getName() + " received: " + message);
            if(!CONFIRM_MESSAGE.equals(message)) {
                sender.close();
                receiver.close();
                socket.close();
                throw new Exception("Error: " + message);
            }
            sender.println(PRODUCE_MESSAGE);
            sender.flush();
            sender.close();
            receiver.close();
            socket.close();
        } catch(Exception e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
    }
            
}