public class Client implements Runnable {
    private Pool pool;
    private Clothes clothes = Clothes.WORN;

    public Client(Pool pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        /* Deadlock se un thread prende la chiave della stanza e poi cerca di prendere la chiave del locker 
        mentre tutte le chiavi del locker sono prese da thread che stanno cercando di prendere la chiave della stanza 
        // a) Takes dressing room key
        pool.Acquire_DressingRoom_Key(); 

        // b) Takes locker key
        pool.Acquire_Locker_Key(); */

        // Per evitare il deadlock faccio prendere entrambe le chiavi subito e le rilascio solo alla fine
        // a) Takes both keys
        pool.AcquireBothKeys();

        // c) Changes clothes in the dressing room
        pool.enterDressingRoom();
        this.clothes = Clothes.CARRIED;

        // d) Leaves the dressing room
        pool.leaveDressingRoom();

        // e) Leaves clothes in the locker
        pool.useLocker();
        this.clothes = Clothes.STORED;

        // f) Leaves dressing room key
        // pool.Release_DressingRoom_Key(); rimosso per evitare deadlock

        // g) Swims
        try {
            System.out.println(Thread.currentThread().getName() + " is swimming.");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }

        /* Rimosso per evitare deadlock
         h) Takes dressing room key
        pool.Acquire_DressingRoom_Key(); 
        */

        // i) Takes clothes from the locker
        pool.emptyLocker();
        this.clothes = Clothes.CARRIED;

        // j) Changes clothes in the dressing room
        pool.enterDressingRoom();
        this.clothes = Clothes.WORN;

        // k) Leaves the dressing room
        pool.leaveDressingRoom();

        // L) Leaves both keys
        // pool.Release_Locker_Key();
        // pool.Release_DressingRoom_Key(); uniti in una funzione sola per evitare deadlock
        pool.ReleaseBothKeys();
    }
}
