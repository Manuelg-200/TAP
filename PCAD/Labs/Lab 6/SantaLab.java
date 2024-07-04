import java.util.concurrent.Semaphore;

public class SantaLab {
    private Semaphore door = new Semaphore(3);
    private static int elvesInside = 0;
    private static Boolean SantGaveHelp = false;

    // For elves, only three at a time can ask santa for help
    public void knock() {
        try {
            door.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // For elves, returns true if exactly three elves are inside
    public synchronized Boolean Enter() {
        elvesInside++;
        try {
            if(elvesInside < 3) {
                wait();
                return false;
            }
            return (elvesInside == 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    //For elves
    public synchronized void Exit() {
        elvesInside--;
        if(elvesInside == 0) {
            SantGaveHelp = false;
            door.release(3);
            notifyAll();
        }
    }

    // For elves, ask for help
    public synchronized void AskForHelp() {
        try {
            while(!SantGaveHelp)
                wait();
            System.out.println(Thread.currentThread().getName() + " got help from Santa");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // For Santa, elves need help
    public synchronized void HelpElves() {
        SantGaveHelp = true;
        notifyAll();
    }
}
