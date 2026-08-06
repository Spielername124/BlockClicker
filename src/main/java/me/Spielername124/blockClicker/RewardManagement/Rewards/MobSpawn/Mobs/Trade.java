package me.Spielername124.blockClicker.RewardManagement.Rewards.MobSpawn.Mobs;

import me.Spielername124.blockClicker.RewardManagement.Rewards.PossibleItemStacks.DroppedItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

//this class stores Trades. This is done so that during runtime amounts of the DroppedItem can be dynamically set
public class Trade {
    public final DroppedItem output;
    public final DroppedItem input1;
    public final DroppedItem input2;
    public final int maxUses;

    public Trade(DroppedItem output, DroppedItem input1, DroppedItem input2, int maxUses) {
        this.output = output;
        this.input1 = input1;
        this.input2 = input2;
        this.maxUses = maxUses;
    }

    public MerchantRecipe createRecipe() {
        MerchantRecipe recipe = new MerchantRecipe(output.getItem(),maxUses);
        recipe.addIngredient(input1.getItem());
        if (input2 != null) {
            ItemStack input2Stack = input2.getItem();
            if (input2Stack != null)
                recipe.addIngredient(input2Stack);
        }
        return recipe;

    }
}
