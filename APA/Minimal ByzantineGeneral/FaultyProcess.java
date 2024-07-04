public class FaultyProcess extends Process {
    private Boolean b;

    public FaultyProcess(int id, int num_processes, Boolean initialB, Boolean[] sent_bits, int T) {
        super(id, num_processes, sent_bits, T);
        this.b = initialB;
    }

    public void sendBit() {
        // Faulty process always sends the opposite of the value it received
        super.sendBit(!b);
    }

    public Boolean MCByzantineGeneral(Boolean coinFlip) {
        super.MCByzantineGeneral(coinFlip);
        return b;
    }
}
