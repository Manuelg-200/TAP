public class ClientTest {
    public static void main(String[] args) {
        Thread producers[] = new Thread[10];
        Thread consumers[] = new Thread[10];
        for(int i = 0; i < 10; i++) {
            producers[i] = new Thread(new TCP_Producer(), "Producer " + i);
            consumers[i] = new Thread(new TCP_Consumer(), "Consumer " + i);
        }
        for(int i = 0; i < 10; i++) {
            producers[i].start();
            consumers[i].start();
        }
        for(int i = 0; i < 10; i++) {
            try {
                producers[i].join();
                consumers[i].join();
            } catch(Exception e) {
                System.out.println("Error: " + e);
                e.printStackTrace();
            }
        }
    }
}
