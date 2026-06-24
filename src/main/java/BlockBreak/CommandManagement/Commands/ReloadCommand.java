package BlockBreak.CommandManagement.Commands;

import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.command.CommandSender;

public class ReloadCommand {
    public static boolean reload(BlockClicker plugin, CommandSender sender){
        plugin.reloadAllConfigs();
        sender.sendMessage("all configs have been reloaded");
        return true;
    }
}
