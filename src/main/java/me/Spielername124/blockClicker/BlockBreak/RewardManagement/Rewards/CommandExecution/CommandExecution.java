package me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.CommandExecution;

import me.Spielername124.blockClicker.BlockBreak.GlobalFlags;
import me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.Reward;
import me.Spielername124.blockClicker.BlockBreak.RewardManagement.Rewards.RewardSound;
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

    private final boolean isExecutedByPlayer;
    private final String command;

    public CommandExecution(BlockClicker plugin, FileConfiguration config, Map<?, ?> rewardData) {
        super(plugin, config, rewardData);

        isExecutedByPlayer = Boolean.TRUE.equals(rewardData.get("executed-by-player"));

         command = rewardData.get("command").toString();







    }

    @Override
    protected void execute(Player player, Location location, GlobalFlags flags, RewardSound sound, ItemStack toolUsed, Block block){

        if(command == null || command.isEmpty()) return;
        //let PAPI process the command with the runtime data
        String processedPAPICommand = PlaceholderAPI.setPlaceholders(player, command);
        if(processedPAPICommand.startsWith("/"))
            processedPAPICommand = processedPAPICommand.substring(1);

        //replace the block location in the command
        String finalCommand = processedPAPICommand
                .replace("%blockXValue%", String.valueOf(location.getBlockX()))
                .replace("%blockYValue%", String.valueOf(location.getBlockY()))
                .replace("%blockZValue%", String.valueOf(location.getBlockZ()));

        //Dispatch the command by either the console or the player
        if(isExecutedByPlayer && player!=null){
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
