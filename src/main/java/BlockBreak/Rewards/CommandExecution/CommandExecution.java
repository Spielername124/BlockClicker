package BlockBreak.Rewards.CommandExecution;

import BlockBreak.GlobalFlags;
import BlockBreak.Rewards.Reward;
import BlockBreak.Rewards.RewardSound;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import me.clip.placeholderapi.PlaceholderAPI;


import java.util.Map;

public class CommandExecution extends Reward {
    public CommandExecution(BlockClicker plugin, FileConfiguration config, Map<?, ?> rewardData, RewardSound sound) {
        super(plugin, config, rewardData, sound);
    }

    @Override
    protected void execute (Player player, Location location, GlobalFlags flags, ItemStack toolUsed, Block block){

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
    }
}
