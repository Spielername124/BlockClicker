package BlockBreak;

import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BlockClickerCommands implements CommandExecutor {
    private final BlockClicker plugin;

    public BlockClickerCommands(BlockClicker plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("blockclicker.admin")) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }
        //invalid commands
        if (args.length == 0) {
            return true;
        }
        String subCommand = args[0];

        switch (subCommand){

            case "reload":
                plugin.reloadAllConfigs();
                sender.sendMessage("all configs have been reloaded");
                return true;

            case "saveitem":
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Can only be used by players");
                    return true;
                }
                if (args.length != 2) {
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

                plugin.getItemsConfig().set("saved-items." + itemId, itemInHand);
                plugin.saveItemsConfig();

                player.sendMessage("Item successfully saved with ID: " + itemId);
                break;


            default:
                sender.sendMessage("Unknown subcommand");
                break;
        }
        return true;
    }
}
