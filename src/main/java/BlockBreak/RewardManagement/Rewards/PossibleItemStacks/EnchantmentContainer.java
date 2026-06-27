package BlockBreak.RewardManagement.Rewards.PossibleItemStacks;

import BlockBreak.RewardManagement.Rewards.RewardsHelper.Amount;
import BlockBreak.RewardManagement.Rewards.RewardsHelper.Chance;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class EnchantmentContainer {

    private final Enchantment enchantment;
    private final Amount amount;
    private final double chance;

    public EnchantmentContainer(Map<?, ?> enchantmentData){
        String typeString = (String) enchantmentData.get("type");
        if (typeString != null) {
            this.enchantment = Enchantment.getByKey(NamespacedKey.minecraft(typeString.toLowerCase().trim()));
        } else {
            this.enchantment = null;
        }

        this.amount = new Amount(enchantmentData);

        Number chanceNr = (Number) enchantmentData.get("chance");
        this.chance = chanceNr != null ? chanceNr.doubleValue() : 100.0;
    }

    public void applyEffectToMeta(ItemMeta meta){
        if (enchantment == null || meta == null) return;

        //roll the dice
        if(!(ThreadLocalRandom.current().nextDouble(0, 100)<= chance)) return;

        meta.addEnchant(enchantment, amount.getAmount(), true);
    }
}
