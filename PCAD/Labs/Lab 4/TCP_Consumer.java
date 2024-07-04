import java.io.*;
import java.net.*;

public class TCP_Consumer implements Runnable {
    private static final String GREET_MESSAGE = "consumer";
    private static final String CONFIRM_MESSAGE = "okcons";

    @Override
    public void run() {
        try {
            Socket socket = new Socket("localhost", 4242);
            BufferedReader receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter sender = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            sender.println(GREET_MESSAGE);
            sender.flush();
            String message = receiver.readLine();
            System.out.println("Received: " + message);
            if(!CONFIRM_MESSAGE.equals(message)) {
                sender.close();
                receiver.close();
                socket.close();
                throw new Exception("Error: " + message);
            }
            message = receiver.readLine();
            System.out.println(Thread.currentThread().getName() +  " received: " + message);
            sender.close();
            receiver.close();
            socket.close();
        } catch(Exception e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
    }
}
