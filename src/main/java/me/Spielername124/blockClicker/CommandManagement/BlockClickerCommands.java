package me.Spielername124.blockClicker.CommandManagement;

import me.Spielername124.blockClicker.CommandManagement.Commands.CustomItemCommands;
import me.Spielername124.blockClicker.CommandManagement.Commands.CustomMobCommand;
import me.Spielername124.blockClicker.CommandManagement.Commands.ReloadCommand;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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

            case "spawnmob":
                return CustomMobCommand.spawnMob(sender, args);

            default:
                sender.sendMessage("Unknown subcommand");
                break;
        }
        return true;
    }
}
