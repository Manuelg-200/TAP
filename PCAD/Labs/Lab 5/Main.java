public class Main {
    static final int CLIENTS_NUM = 50;
    static Pool pool;

    public static void main(String[] args) {
        int dressingRoom_Num, locker_Num;
        Thread clients[] = new Thread[CLIENTS_NUM];

        System.out.println("Insert amount of lockers: ");
        locker_Num = Integer.parseInt(System.console().readLine());
        System.out.println("Insert amount of dressing rooms: ");
        dressingRoom_Num = Integer.parseInt(System.console().readLine());
        pool = new Pool(dressingRoom_Num, locker_Num);

        for(int i=0; i < CLIENTS_NUM; i++) {
            clients[i] = new Thread(new Client(pool), "Client " + i);
            clients[i].start();
        }

        for(int i=0; i < CLIENTS_NUM; i++) {
            try {
                clients[i].join();
            } catch (Exception e) {
                e.getMessage();
                e.printStackTrace();
            }
        }
    }
}
