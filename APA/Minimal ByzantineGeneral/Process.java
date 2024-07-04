public class Process {
    private final int id;
    private final int num_processes;
    private Boolean[] sent_bits;
    private final int T; // T = 2*faulty_processes + 1

    public Process(int id, int num_processes, Boolean[] sent_bits, int T) {
        this.id = id;
        this.num_processes = num_processes;
        this.sent_bits = sent_bits;
        this.T = T;
    }

    public int getId() {
        return id;
    }
    
    // First step of the MCByzantineGeneral algorithm
    protected void sendBit(Boolean b) {
        sent_bits[this.id] = b;
    }

    // Rest of the MCByzantineGeneral algorithm
    protected Boolean MCByzantineGeneral(Boolean coinFlip) {
        var tally = 0;
        var numTrue = 0;
        Boolean majorityBit;
        // 2. Bits are already received and stored in the shared sent_bits array
        // 3. Compute the majority bit; 4. Find tally
        for (Boolean bit : sent_bits)
            if (bit)
                numTrue++;
        if(numTrue > num_processes / 2) {
            tally = numTrue;
            majorityBit = true;
        }
        tally = num_processes - numTrue;
        majorityBit = false;
        // 5. final if
        if(tally >= T)
            return majorityBit;
        else {
            return coinFlip;
        }
    }
}
