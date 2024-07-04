import java.util.concurrent.Semaphore;

public class Pool {
    private Semaphore locker_keys;
    private Semaphore dressingRoom_keys;

    public Pool(int dressingRoom_Num, int lockers_Num) {
        dressingRoom_keys = new Semaphore(dressingRoom_Num);
        locker_keys = new Semaphore(lockers_Num);
    }

    public void Acquire_DressingRoom_Key() {
        try {
            dressingRoom_keys.acquire();
            System.out.println(Thread.currentThread().getName() + " has acquired a dressing room key.");
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    public void Release_DressingRoom_Key() {
        try {
            dressingRoom_keys.release();
            System.out.println(Thread.currentThread().getName() + " has released a dressing room key.");
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    public void Acquire_Locker_Key() {
        try {
            locker_keys.acquire();
            System.out.println(Thread.currentThread().getName() + " has acquired a locker key.");
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    // Per evitare il deadlock
    public void AcquireBothKeys() {
        try {
            dressingRoom_keys.acquire();
            locker_keys.acquire();
            System.out.println(Thread.currentThread().getName() + " has acquired both keys.");
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    // Per evitare il deadlock
    public void ReleaseBothKeys() {
        try {
            dressingRoom_keys.release();
            locker_keys.release();
            System.out.println(Thread.currentThread().getName() + " has released both keys.");
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    public void Release_Locker_Key() {
        try {
            locker_keys.release();
            System.out.println(Thread.currentThread().getName() + " has released a locker key.");
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    public void enterDressingRoom() {
        System.out.println(Thread.currentThread().getName() + " is changing clothes in the dressing room.");
    }

    public void leaveDressingRoom() {
        System.out.println(Thread.currentThread().getName() + " has left the dressing room.");
    }

    public void useLocker() {
        System.out.println(Thread.currentThread().getName() + " is leaving clothes in the locker.");
    }

    public void emptyLocker() {
        System.out.println(Thread.currentThread().getName() + " is taking clothes from the locker.");
    }
}
