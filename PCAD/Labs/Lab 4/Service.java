import java.net.*;
import java.io.*;

public class Service implements Runnable {
    private static final String CONSUMER_GREET = "consumer";
    private static final String PRODUCER_GREET = "producer";
    private static final String CONSUMER_CONFIRM = "okcons";
    private static final String PRODUCER_CONFIRM = "okprod";

    private Socket client;
    private My_Queue queue;

    public Service(Socket client, My_Queue queue) {
        this.client = client;
        this.queue = queue;
    }

    private void ServeConsumer(PrintWriter sender) {
        String message = queue.Read();
        sender.println(message);
        sender.flush();
    }

    private void ServeProducer(BufferedReader receiver) {
        try {
            String message = receiver.readLine();
            queue.Write(message);
        } catch(Exception e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            BufferedReader receiver = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter sender = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
            String message = receiver.readLine();
            System.out.println("Server " + Thread.currentThread().getName() + " received: " + message);
            if(CONSUMER_GREET.equals(message)) { 
                sender.println(CONSUMER_CONFIRM);
                sender.flush();
                ServeConsumer(sender);
            }
            else if(PRODUCER_GREET.equals(message)) {
                sender.println(PRODUCER_CONFIRM);
                sender.flush();
                ServeProducer(receiver);
            }
            sender.close();
            receiver.close();
            client.close();
        } catch(Exception e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
    }
}