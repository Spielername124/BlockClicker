package BlockBreak.CommandManagement;

import BlockBreak.CommandManagement.Commands.CustomItemCommands;
import BlockBreak.CommandManagement.Commands.ReloadCommand;
import me.Spielername124.blockClicker.BlockClicker;
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

        //make it all lowercase to eliminate case sensitivity
        subCommand = subCommand.toLowerCase();

        switch (subCommand){

            case "reload":
                return ReloadCommand.reload(plugin, sender);

            case "saveitem":
                return CustomItemCommands.saveItem(plugin, sender, args);

            case "getitem":
                return CustomItemCommands.getItem(plugin, sender, args);

            case "getmetadata":
                return CustomItemCommands.getItemMetadata(sender);

            default:
                sender.sendMessage("Unknown subcommand");
                break;
        }
        return true;
    }
}
