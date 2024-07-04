import java.util.Random;
import java.util.ArrayList;

public class LVQuicksort {
    private Random random;
    private Integer comparisonCounter = 0;

    public LVQuicksort() {
        random = new Random();
    }
        
    public Integer getComparisonCounter() {
        return comparisonCounter;
    }

    public ArrayList<Integer> sort(ArrayList<Integer> sequence) {
        if(sequence.size() <= 1)
            return sequence;

        Integer pivot = sequence.get(random.nextInt(sequence.size()));
        ArrayList<Integer> less = new ArrayList<Integer>();
        ArrayList<Integer> greater = new ArrayList<Integer>();

        for(Integer i : sequence) {
            comparisonCounter++;
            if(i < pivot)
                less.add(i);
            else if(i >= pivot)
                greater.add(i);
        }

        less = sort(less);
        greater = sort(greater);
        less.addAll(greater);
        return less;
    }
}
