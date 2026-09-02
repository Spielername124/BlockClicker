package me.Spielername124.blockClicker.RewardManagement.Rewards.MobSpawn.Mobs;

import me.Spielername124.blockClicker.BlockClicker;
import me.Spielername124.blockClicker.EventWideFlags;
import me.Spielername124.blockClicker.GlobalFlags;
import me.Spielername124.blockClicker.RewardManagement.Rewards.PossibleItemStacks.DroppedItem;
import me.Spielername124.blockClicker.RewardManagement.Rewards.RewardsHelper.Amount;
import me.Spielername124.blockClicker.RewardManagement.Rewards.RewardsHelper.WeightedList;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Trader extends SpawnableMob{


    private final WeightedList<Trade> weightedTradeList;
    private final Amount amount;
    private final Villager.Profession profession;



    public Trader(BlockClicker plugin, GlobalFlags flags, Map<?, ?> rewardData, EntityType type) {
        super(plugin, rewardData, type);

        weightedTradeList = new WeightedList<Trade>();


        //get the amounts of trades
        amount = (rewardData.get("number-of-trades") instanceof  Map<?, ?> amountMap) ?
                new Amount(amountMap):
                new Amount(Integer.MAX_VALUE);

        //sets a profession for the villager, which is needed in case that the trader is not a wandering trader
        profession = rewardData.get("profession") instanceof String s ?
                Registry.VILLAGER_PROFESSION.get(NamespacedKey.minecraft(s.toLowerCase())) :
                null;

        processTrades(plugin, flags, rewardData);
    }

    //processes the rewardData to usable Trades
    private void processTrades(BlockClicker plugin, GlobalFlags flags, Map<?, ?> rewardData){

        Object tradesObject = rewardData.get("trades");
        List<?> rawTrades = tradesObject instanceof List ? (List<?>) tradesObject : null;


        List<Trade> trades = new ArrayList<>();
        if(rawTrades == null) return;

        for(Object rawTrade : rawTrades){
            //if there is no valid trade continue, else get it
            if (!(rawTrade instanceof Map<?, ?> tradeMap)) continue;

            //checks if the output item is defined in the correct form of a map and creates the output item
            if(!(tradeMap.get("output") instanceof  Map<?, ?> outputMap)) continue;
            DroppedItem output = DroppedItem.create(plugin, outputMap);

            if(!(tradeMap.get("input1") instanceof  Map<?, ?> input1Map)) continue;
            DroppedItem input1 = DroppedItem.create(plugin, input1Map);

            //if either Input or output are null, is this not a valid trade
            if(output == null || input1 == null) continue;

            //set input2 if existing
            DroppedItem input2 = null;
            if((tradeMap.get("input2") instanceof  Map<?, ?> input2Map))
                 input2 = DroppedItem.create(plugin, input2Map);

            //get the max uses of a trade
            Object maxUsesRaw = tradeMap.get("max-uses");
            int maxUses = maxUsesRaw instanceof Number number? number.intValue(): flags.maxTradeUsage;

            Object weightRaw = tradeMap.get("weight");
            double weight = weightRaw instanceof Number number? number.doubleValue(): 1;

            weightedTradeList.addElement(new Trade(output, input1, input2, maxUses),weight);

        }
    }


    @Override
    public LivingEntity spawn(BlockClicker plugin, GlobalFlags flags, Location location, EventWideFlags eventWideFlags) {
        LivingEntity spawnedMob = super.spawn(plugin, flags, location, eventWideFlags);
        if (spawnedMob == null) return null;

        //gets the stated amount of random trades
        List<Trade> trades = weightedTradeList.getXRandomElements(amount.getAmount()) ;

        //convert these trades to recipes and add them to the trade list of the trader
        ArrayList<MerchantRecipe> recipes = new ArrayList<>();
        for (Trade trade : trades) {
            recipes.add(trade.createRecipe());
        }
        //we know this cast will work, since this mob is only able to be a AbstractVillager
        if (spawnedMob instanceof Villager villager) {

            //sets the villager profession, defaults to FLETCHER
            villager.setProfession(profession != null ? profession : Villager.Profession.FLETCHER);
            villager.setVillagerLevel(5);
        }

        //overwrites the Traders loot after one tick, so that not the basic trades overwrite the custom trades
        plugin.getServer().getScheduler().runTask(plugin, () -> {
            if (spawnedMob.isValid()) {
                ((AbstractVillager) spawnedMob).setRecipes(recipes);
            }
        });

        return spawnedMob;
    }



}
