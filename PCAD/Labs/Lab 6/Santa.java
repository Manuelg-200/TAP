public class Santa implements Runnable {
    private SantaBed SantaBed;
    private HeatedStable heatedStable;
    private Sleigh sleigh;
    private SantaLab santaLab;

    public Santa(SantaBed SantaBed, HeatedStable heatedStable, Sleigh sleigh, SantaLab santaLab) {
        this.SantaBed = SantaBed;
        this.heatedStable = heatedStable;
        this.sleigh = sleigh;
        this.santaLab = santaLab;
    }

    public void run() {
        while(true) {
            // Santa is sleeping
            var whoIsIt = SantaBed.sleep();

            
            if(whoIsIt == WhoIsIt.REINDEERS) {
                // The reindeers are back and are in the stables
                heatedStable.leadReindeers();
                // Santa is going to deliver presents with the reindeers
                sleigh.deliverPresents();
            } else if(whoIsIt == WhoIsIt.ELVES) {
                // The elves need help
                santaLab.HelpElves();
            } else {
                // Both the reindeers are back and the elves need help, reindeers have priority
                heatedStable.leadReindeers();
                sleigh.deliverPresents();
                santaLab.HelpElves();
            }
        }
    }
}
