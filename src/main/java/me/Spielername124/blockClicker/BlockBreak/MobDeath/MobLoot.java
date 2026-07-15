package me.Spielername124.blockClicker.BlockBreak.MobDeath;

import me.Spielername124.blockClicker.BlockBreak.GlobalFlags;
import me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.PossibleItemStacks.CustomItemDrop;
import me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.PossibleItemStacks.DroppedItem;
import me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.PossibleItemStacks.NormalItemDrop;
import me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.RewardsHelper.Amount;
import me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.RewardsHelper.Chance;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class MobLoot {

    private final DroppedItem droppedItem;
    private final Amount amount;
    private final double chance;


    public MobLoot(BlockClicker plugin,  Map<?, ?> rewardData) {

        boolean isSpecialItem = Boolean.TRUE.equals(rewardData.get("is-custom"));
        amount = new Amount(rewardData);
        droppedItem = isSpecialItem ? new CustomItemDrop(plugin, rewardData) : new NormalItemDrop(rewardData);

        Number chanceNr = (Number) rewardData.get("chance");
        chance = chanceNr != null ? chanceNr.doubleValue() : 100;
    }

    public void rollAndExecute(GlobalFlags flags, Location location, Player player) {
        double randomRoll = ThreadLocalRandom.current().nextDouble(100.0);
        //return empty if the roll is higher than the needed one
        if (randomRoll > chance) return;

        if(flags.depositToInventory && player !=null)
            player.getInventory().addItem(droppedItem.getItem());

        else
            location.getWorld().dropItemNaturally(location, droppedItem.getItem());
    }

}
