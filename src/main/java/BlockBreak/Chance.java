package BlockBreak;

import java.util.concurrent.ThreadLocalRandom;

public class Chance {
    public static boolean performDropRoll(GlobalFlags flags, double chance){
        double randomRoll = ThreadLocalRandom.current().nextDouble(100.0);

        return randomRoll <= chance;
    }
}
