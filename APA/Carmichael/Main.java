import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

public class Main {
    private static final int[] TEST_NUMBERS = {561, 1105, 1729, 2465, 2821, 6601, 8911};

    private static Boolean PrimalityTest(int n, int a) {
        var s = 0;
        var q = n - 1;
        while(q % 2 == 0) {
            s++;
            q /= 2;
        }
        // Step 4 jumped
        var x = BigInteger.valueOf(a).modPow(BigInteger.valueOf(q), BigInteger.valueOf(n)).intValue();
        if(x == 1 || x == n-1)
            return true; // probabilmente primo
        while(s - 1 >= 0) {
            x = (x * x) % n;
            if(x == n-1)
                return true; // probabilmente primo
            s--;
        }
        return false; // composto
    }

    public static void main(String[] args) throws Exception {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt"));
            for (int number : TEST_NUMBERS) {
                writer.write("Number: " + number);
                writer.newLine();
                // Set a outside the algorithm
                for(int a = 2; a <= number - 2; a++) {
                    if(PrimalityTest(number, a))
                        writer.write(a + "; ");
                }
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
 