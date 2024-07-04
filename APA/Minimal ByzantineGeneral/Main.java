import java.util.Random;
import java.io.FileWriter;

public class Main {
    private static final int NUM_RUN = 1024;
    private static final int NUM_PROCESSES = 4;
    private static final Random coin = new Random();

    // Array of bit transmitted each round for each process
    private static Boolean[] SENT_BITS = new Boolean[NUM_PROCESSES];
    // Array of final bits for each process
    private static Boolean[] FINAL_BITS = new Boolean[NUM_PROCESSES];

    private static Boolean checkResults() {
        for (int i = 0; i < NUM_PROCESSES - 1; i++)
            if (!FINAL_BITS[i].equals(FINAL_BITS[i + 1]))
                return false;
        return true;
    }

    private static void saveRoundCountToFile(int roundCount) {
        try {
            FileWriter fileWriter = new FileWriter("roundCount.txt", true);
            fileWriter.write(roundCount + "");
            fileWriter.write("\n");
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        GoodProcess[] goodProcesses = new GoodProcess[NUM_PROCESSES - 1];

        for(int i=0; i<NUM_RUN; i++) {
            for (int j = 0; j < NUM_PROCESSES - 1; j++)
                goodProcesses[j] = new GoodProcess(j, NUM_PROCESSES, j%2 == 0 ? false : true, SENT_BITS, 3); // T = 3
            FaultyProcess faultyProcess = new FaultyProcess(3, NUM_PROCESSES, false, SENT_BITS, 3); // T = 3
            var roundCounter = 0;

            do {
                roundCounter++;
                var coinFlip = coin.nextBoolean();
                // MCByzantineGeneral is called for each process
                for (GoodProcess goodProcess : goodProcesses)
                    goodProcess.sendBit();
                faultyProcess.sendBit();

                for (GoodProcess goodProcess : goodProcesses)
                    FINAL_BITS[goodProcess.getId()] = goodProcess.MCByzantineGeneral(coinFlip);
                FINAL_BITS[3] = faultyProcess.MCByzantineGeneral(coinFlip);
                } while (!checkResults());
                saveRoundCountToFile(roundCounter);
        }
    }
}
