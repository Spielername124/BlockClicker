package BlockBreak.RewardManagement.Rewards.RewardsHelper;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
public class Amount {


    //sets the amount to a fixed int, dynamically using the set amount and the bounds. (if a amount is set by itself it has a higher priority)
    public static int getAmount(Map<?, ?> rewardData){

        //if a specific amount is set, we return this amount
        Number amountNr = (Number) rewardData.get("amount");
        if (amountNr != null) return amountNr.intValue();

        //polling for set bounds
        Number upperBoundNr = (Number) rewardData.get("amount-upper-bound");
        Number lowerBoundNr = (Number) rewardData.get("amount-lower-bound");

        // if neither a amount nor a bound is set, we default back to an amount of 1
        if(upperBoundNr == null && lowerBoundNr == null) return 1;

        //we use as default for bounds 0 and 64
        int lowerBound = lowerBoundNr != null ? lowerBoundNr.intValue() : 0;
        int upperBound = upperBoundNr != null ? upperBoundNr.intValue() : 64;

        //return a random value within the bounds
        return ThreadLocalRandom.current().nextInt(lowerBound, upperBound+1);

    }
}
