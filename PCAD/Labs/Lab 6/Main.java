public class Main {
    private static final int REINDEER_NUM = 9;
    private static final int ELF_NUM = 40;

    private static SantaBed santaBed = new SantaBed();
    private static HeatedStable heatedStable = new HeatedStable();
    private static Sleigh sleigh = new Sleigh();
    private static SantaLab santaLab = new SantaLab();

    public static void main(String[] args) throws Exception {
        Thread Santa = new Thread(new Santa(santaBed, heatedStable, sleigh, santaLab));
        Thread Reindeer[] = new Thread[REINDEER_NUM];
        for(int i=0; i<REINDEER_NUM; i++) 
            Reindeer[i] = new Thread(new Reindeer(heatedStable, santaBed, sleigh), "Reindeer " + i);
        Thread Elf[] = new Thread[ELF_NUM];
        for(int i=0; i<ELF_NUM; i++) 
            Elf[i] = new Thread(new Elf(santaLab, santaBed), "Elf " + i);

        Santa.start();
        for(int i=0; i<REINDEER_NUM; i++) 
            Reindeer[i].start();
        for(int i=0; i<ELF_NUM; i++) 
            Elf[i].start();
    }
}
