import java.net.*;
import java.util.*;

public class TCPServer {
    private static final int PORT = 4242;

    private static ArrayList<Thread> threads = new ArrayList<Thread>();
    private static ArrayList<Socket> clients = new ArrayList<Socket>();

    private static void AnswerClient(Socket client, My_Queue queue) {
        try {
            threads.add(new Thread(new Service(client, queue), threads.size() + ""));
            threads.get(threads.size() - 1).start();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        My_Queue queue = new My_Queue();
        try (ServerSocket server = new ServerSocket(PORT)) {
            // Server loop
            while(true) {
                var client = server.accept();
                clients.add(client);
                AnswerClient(client, queue);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        } finally {
            for(Thread t : threads) {
                try {
                    t.join();
                } catch (Exception e) {
                    System.out.println("Error: " + e);
                    e.printStackTrace();
                }
            }
            for(Socket c : clients) {
                try {
                    c.close();
                } catch (Exception e) {
                    System.out.println("Error: " + e);
                    e.printStackTrace();
                }
            }
        }
    }
}
