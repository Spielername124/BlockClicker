package me.Spielername124.blockClicker.RewardManagement.Rewards.ReplaceBlock;

import me.Spielername124.blockClicker.BlockClicker;
import me.Spielername124.blockClicker.EventWideFlags;
import me.Spielername124.blockClicker.GlobalFlags;
import me.Spielername124.blockClicker.RewardManagement.Rewards.Reward;
import me.Spielername124.blockClicker.RewardManagement.Rewards.RewardSoundAndParticle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class SetBlock extends Reward {
    private final Material material;
    private final boolean onPlayer;

    public SetBlock(BlockClicker plugin, FileConfiguration config, Map<?, ?> rewardData) {
        super(plugin, config, rewardData);
        String  materialString = (String) rewardData.get("set_block");
        if(materialString==null){
            plugin.getLogger().warning("Invalid block block material!");
            materialString="";
        }

        //gets whether the block is placed on the player or the location of the removed block
        Object rawOnPlayer = rewardData.get("on_player");
        onPlayer = rawOnPlayer != null && (boolean) rawOnPlayer;
        material = Material.matchMaterial(materialString) != null ? Material.matchMaterial(materialString) : Material.AIR ;


    }

    @Override
    protected void execute(Player player, Location location, GlobalFlags flags, RewardSoundAndParticle sound, ItemStack toolUsed, Block block, EventWideFlags eventWideFlags) {
        if(!onPlayer){
            plugin.getServer().getScheduler().runTask(plugin, () -> {

                block.setType(material);
            });
        }
        else{
            //sets the block on the player
            player.getLocation().getBlock().setType(material);
        }
    }
}
