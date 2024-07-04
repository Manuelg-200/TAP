import java.util.HashSet;
import java.util.Set;

public class CreditAccount {
    public static final int default_limit = 10000;

    private static Set<Long> idList = new HashSet<>();
    private int limit = default_limit;
    private int balance;
    private final Person owner;
    private final long id;

    private void checkBalance(int balance) {
        if(balance < this.limit)
            throw new IllegalArgumentException();
    }

    private void checkId(long id) {
        if(id < 0)
            throw new IllegalArgumentException();
        if(!idList.add(id))
            throw new IllegalArgumentException();
    }

    private CreditAccount(int limit, int balance, Person owner, long id) {
        checkBalance(balance);
        checkId(id);
        this.limit = limit;
        this.balance = balance;
        this.owner = owner;
        this.id = id;
    }

    public static CreditAccount newOfLimitBalanceOwner(int limit, int balance, Person owner) {
        CreditAccount tmp = new CreditAccount(limit, balance, owner, 0);
        return tmp;
    }

    public static CreditAccount newOfLimitBlanceOwner(int balance, Person owner) {
        CreditAccount tmp = new CreditAccount(default_limit, balance, owner, 0);
        return tmp;
    }

    public int getLimit() {
        return this.limit;
    }

    public int getBalance() {
        return this.balance;
    }

    public Person getOwner() {
        return this.owner;
    }

    public long getId() {
        return this.id;
    }

    private static void checkPositive(int a) {
        if(a < 0)
            throw new IllegalArgumentException();
    }

    public int deposit(int amount) {
        checkPositive(amount);
        this.balance += amount;
        return this.balance;
    }

    public int withdraw(int amount) {
        checkPositive(amount);
        this.balance -= amount;
        checkBalance(this.balance);
        return this.balance;
    }

    public void setLimit(int limit) {
        this.limit = limit;
        checkBalance(this.balance);
    }
}
