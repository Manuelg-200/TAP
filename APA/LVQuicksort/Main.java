import java.util.ArrayList;
import java.util.Collections;
import java.io.PrintWriter;

public class Main {
    final static int RUN_NUMBER =  100000;
    final static int SEQUENCE_SIZE = 10000;

    private static Double CalculateAverage(Integer[] comparisonCounterResults) {
        Double sum = 0.0;
        for(int i=0; i < RUN_NUMBER; i++)
            sum += comparisonCounterResults[i];
        return sum / (double)RUN_NUMBER;
    }

    private static Double CalculateStandardDeviation(Integer[] comparisonCounterResults, Double averageValue) {
        Double sum = 0.0;
        for(int i=0; i < RUN_NUMBER; i++)
            sum += Math.pow((double)comparisonCounterResults[i] - averageValue, 2);
        return sum / ((double)RUN_NUMBER - 1);
    }

    private static void ExportResults(Integer[] comparisonCounterResults) {
        try {
            PrintWriter writer = new PrintWriter("results.txt", "UTF-8");
            writer.println(RUN_NUMBER);
            for(int i=0; i < RUN_NUMBER; i++)
                writer.println(comparisonCounterResults[i]);
            writer.close();
        } catch(Exception e) {
            System.out.println("Error while exporting results: " + e.getMessage());
        }
    }

    private static void CheckExpectedLimits(Integer[] comparisonCounterResults, Double averageValue, Double standardDeviation) {
        // Doppio e triplo del valore atteso (v=2, v=3) prima disuguaglianza
        for(Double v=2.0; v<=3; v++) {
            int favorableResults = 0;
            for(Integer i : comparisonCounterResults) {
                if(i >= v * averageValue)
                    favorableResults++;
            }
            Double probability = (double)favorableResults / (double)RUN_NUMBER;
            System.out.println("Check limiti: Probabilità=" + probability.toString());
            // Prima disuguaglianza
            if(probability <= (1/v)) 
                System.out.println("Limite prima disuguaglianza per v=" + v + " OK");
            else
                System.out.println("Limite prima disuguaglianza per v=" + v + " FAIL");
            // Seconda disuguaglianza
            if(probability <= standardDeviation / ((v - 1)*(v - 1)*averageValue*averageValue))
                System.out.println("Limite seconda disuguaglianza per v=" + v + " OK");
            else
                System.out.println("Limite seconda disuguaglianza per v=" + v + " FAIL");
        }
    }

    public static void main(String[] args) throws Exception {
        ArrayList<Integer> sequence = new ArrayList<Integer>(SEQUENCE_SIZE);
        for(int i = 0; i < SEQUENCE_SIZE; i++)
            sequence.add(i);
        Collections.shuffle(sequence);
        Integer[] comparisonCounterResults = new Integer[RUN_NUMBER];

        // 100000 run
        for(int i=0; i < RUN_NUMBER; i++) {
            LVQuicksort quicksort = new LVQuicksort();
            quicksort.sort(sequence);
            comparisonCounterResults[i] = quicksort.getComparisonCounter();
        }

        Double averageValue = CalculateAverage(comparisonCounterResults);
        Double standardDeviation = CalculateStandardDeviation(comparisonCounterResults, averageValue);
        CheckExpectedLimits(comparisonCounterResults, averageValue, standardDeviation);
        System.out.println("Average value: " + averageValue.toString());
        System.out.println("Standard deviation: " + standardDeviation.toString());
        ExportResults(comparisonCounterResults);
    }
}
