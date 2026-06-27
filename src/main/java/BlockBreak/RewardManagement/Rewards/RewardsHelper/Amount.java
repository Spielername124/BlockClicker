package BlockBreak.RewardManagement.Rewards.RewardsHelper;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
public class Amount {

    private final int amount;

    private final int lowerBound;
    private final int upperBound;

    public Amount(Map<?, ?> rewardData){

        //sets the amount to a fixed int, dynamically using the set amount and the bounds. (if a amount is set by itself it has a higher priority)
        Number amountNr = (Number) rewardData.get("amount");
        Number upperBoundNr = (Number) rewardData.get("amount-upper-bound");
        Number lowerBoundNr = (Number) rewardData.get("amount-lower-bound");

        //default to an amount of 1, if neither the amount nor bound are set, else set the amount
        if(amountNr == null && upperBoundNr == null && lowerBoundNr == null){
            amount = 1;
            lowerBound = -1;
            upperBound = -1;
            return;
        }

        amount = (amountNr != null) ? amountNr.intValue() : -1;

        //we use as default for bounds 0 and 64
        lowerBound = lowerBoundNr != null ? lowerBoundNr.intValue() : 0;
        upperBound = upperBoundNr != null ? upperBoundNr.intValue() : 64;


    }

    public int getAmount(){

        //if a specific amount is set, we return this amount
        if (amount != -1) return amount;

        //else return a random value within the bounds
        return ThreadLocalRandom.current().nextInt(lowerBound, upperBound+1);

    }
}
