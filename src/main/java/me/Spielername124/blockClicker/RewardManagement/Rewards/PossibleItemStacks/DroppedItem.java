package me.Spielername124.blockClicker.RewardManagement.Rewards.PossibleItemStacks;

import me.Spielername124.blockClicker.BlockClicker;
import me.Spielername124.blockClicker.RewardManagement.Rewards.RewardsHelper.Amount;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class DroppedItem {
    final Amount amount;
    final String itemType;
    final String itemName;
    final Number durability;
    final List<EnchantmentContainer> enchantmentsList = new ArrayList<>();

    public DroppedItem(Map<?, ?> rewardData){

        itemType = (String) rewardData.get("item");
        amount = new Amount(rewardData);
        itemName = (String) rewardData.get("name");
        durability = (Number) rewardData.get("durability");


        Object rawEnchantments = rewardData.get("enchantment");
        if (rawEnchantments instanceof List<?>) {
            for (Object element : (List<?>) rawEnchantments) {
                if (element instanceof Map<?, ?> enchantmentMap) {
                    enchantmentsList.add(new EnchantmentContainer(enchantmentMap));
                }
            }
        }
    }
    public static DroppedItem create(BlockClicker plugin, Map<?,?> rewardData){

        String itemName = (String) rewardData.get("item");
        if(Objects.equals(itemName, "xp")) return null;

        return (Boolean.TRUE.equals(rewardData.get("is-custom")))?
                new CustomItemDrop(plugin, rewardData):
                new NormalItemDrop(rewardData);
    }

    public ItemStack getItem(){
        ItemStack item = getItemStack();
        if (item == null)
            return null;

        ItemStack reward = item.clone();

        //edit the item according to the specified details in the config
        reward.setAmount(amount.getAmount());

        ItemMeta meta = reward.getItemMeta();
        if(itemName!=null && !itemName.isEmpty()) {
            Component customName = Component.text(itemName);
            meta.displayName(customName);
        }

        if(durability != null){
            Damageable dmgMeta = (Damageable) meta;

            int maxDurability = reward.getType().getMaxDurability();
            int desiredDamage = maxDurability - durability.intValue();
            int finalDamage = Math.max(0, desiredDamage);
            dmgMeta.setDamage(finalDamage);
        }

        for(EnchantmentContainer enchantment : enchantmentsList)
            enchantment.applyEffectToMeta(meta);

        reward.setItemMeta(meta);
        return reward;
    }

    public abstract ItemStack getItemStack();


}
