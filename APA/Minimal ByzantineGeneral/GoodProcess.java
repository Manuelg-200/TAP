public class GoodProcess extends Process {
    private Boolean b;

    public GoodProcess(int id, int num_processes, Boolean initialB, Boolean[] sent_bits, int T) {
        super(id, num_processes, sent_bits, T);
        this.b = initialB;
    }

    public void sendBit() {
        // Good process always sends the value it received
        super.sendBit(b);
    }

    public Boolean MCByzantineGeneral(Boolean coinFlip) {
        this.b = super.MCByzantineGeneral(coinFlip);
        return b;
    }
}
