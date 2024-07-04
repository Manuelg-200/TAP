package Es1;
public class RWbasic {
    private int data = 0;

    public int read() {
        return data;
    }

    public void write() {
        var tmp = data;
        tmp++;
        data = tmp;
    }
}