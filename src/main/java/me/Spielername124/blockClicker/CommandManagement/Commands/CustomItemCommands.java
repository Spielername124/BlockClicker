package me.Spielername124.blockClicker.CommandManagement.Commands;

import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class CustomItemCommands {
    public static boolean saveItem(BlockClicker plugin, CommandSender sender, String[] args){
        if (!(sender instanceof Player)) {
            sender.sendMessage("Can only be used by players");
            return true;
        }
        if (args.length <= 2) {
            sender.sendMessage("Item not saved, you need to provide a Item name");
            return true;
        }

        Player player = (Player) sender;
        String itemId = args[1];
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand.getType() == Material.AIR) {
            player.sendMessage("You must hold an item in your main hand.");
            return true;
        }

        //set the id on the holding item by accessing at changing its meta
        ItemMeta meta = itemInHand.getItemMeta();
        if (meta != null) {
            meta.getPersistentDataContainer().set(
                    BlockClicker.TOOL_ID_KEY,
                    PersistentDataType.STRING,
                    itemId
            );
            //increases the max stack size, if requested
            if(args.length == 3) {
                meta.setMaxStackSize(Integer.valueOf(args[2]));
            }

            itemInHand.setItemMeta(meta);
        }

        //save the tagged item
        plugin.getItemsConfig().set("saved-items." + itemId, itemInHand);
        plugin.saveItemsConfig();

        player.sendMessage("Item successfully saved with ID: " + itemId);
        return true;
    }

    public static boolean getItem(BlockClicker plugin, CommandSender sender, String[] args){
        if(!(sender instanceof Player)){
            sender.sendMessage("Can only be used by players.");
            return true;
        }
        if (args.length != 2) {
            sender.sendMessage("Usage: /blockclicker giveitem <item_id>");
            return true;
        }

        Player player = (Player) sender;
        String ItemId = args[1];

        //retrieve the item
        ItemStack savedItem = plugin.getItemsConfig().getItemStack("saved-items." + ItemId);
        if (savedItem == null) {
            player.sendMessage("Item with ID '" + ItemId + "' does not exist.");
            return true;
        }

        ItemStack droppingItem = savedItem.clone();

        //place it in the inventory if possible, save the item if it was not able to being dropped properly
        HashMap<Integer, ItemStack> nonStorableItems =  player.getInventory().addItem(droppingItem);

        if(!nonStorableItems.isEmpty()){
            player.sendMessage("you were had not enough inventory space to receive the item. If possible, you've received it partially.");
        }
        return true;
    }

    //returns the saved Metadata of the item hold by the player
    public static boolean getItemMetadata(CommandSender sender){
        if(!(sender instanceof Player)){
            sender.sendMessage("Can only be used by players.");
            return true;
        }
        Player player = (Player) sender;

        ItemStack itemToInspect = player.getInventory().getItemInMainHand();

        ItemMeta metadata = itemToInspect.getItemMeta();

        if(metadata == null){
            player.sendMessage("This item does not contain any metadata.");
            return true;
        }

        String toolId = metadata.getPersistentDataContainer().get(
                BlockClicker.TOOL_ID_KEY,
                PersistentDataType.STRING
        );

        if (toolId != null) {
            player.sendMessage("The custom tool ID is: " + toolId);
        } else {
            player.sendMessage("This item does not contain a registered custom tool ID.");
        }
        return true;
    }
}
