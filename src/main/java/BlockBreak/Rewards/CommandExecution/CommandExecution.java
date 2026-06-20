package BlockBreak.Rewards.CommandExecution;

import BlockBreak.GlobalFlags;
import BlockBreak.Rewards.RewardSound;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import me.clip.placeholderapi.PlaceholderAPI;


import java.util.Map;

public class CommandExecution {
    public static void performCommandExecution(BlockClicker plugin, RewardSound sound, Map<?, ?> rewardData, Player player, Location location){

        //get the necessary config data
        String command = rewardData.get("command").toString();
        boolean isExecutedByPlayer = Boolean.TRUE.equals(rewardData.get("executed-by-player"));

        if(command == null || command.isEmpty()) return;

        //let PAPI process the command
        String processedPAPICommand = PlaceholderAPI.setPlaceholders(player, command);
        if(processedPAPICommand.startsWith("/"))
            processedPAPICommand = processedPAPICommand.substring(1);

        //replace the block location in the command
        String finalCommand = processedPAPICommand
                .replace("%blockXValue%", String.valueOf(location.getBlockX()))
                .replace("%blockYValue%", String.valueOf(location.getBlockY()))
                .replace("%blockZValue%", String.valueOf(location.getBlockZ()));

        //Dispatch the command by either the console or the player
        if(isExecutedByPlayer){
            Bukkit.getScheduler().runTask(plugin, () -> {
                Bukkit.dispatchCommand(player, finalCommand);
            });
        }
        else {
            Bukkit.getScheduler().runTask(plugin, () -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), finalCommand);
            });
        }


        //set the sound
        sound.setSound(rewardData);
    }
}
