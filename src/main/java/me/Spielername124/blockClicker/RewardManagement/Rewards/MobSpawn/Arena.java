package me.Spielername124.blockClicker.RewardManagement.Rewards.MobSpawn;

import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Arena {
    public static void buildArena (BlockClicker plugin, Location location, int Y, String form){
        World world = location.getWorld();
        int originX = location.getBlockX();
        int originY =  Y;
        int originZ = location.getBlockZ();

        if(form.equals("5x3x5")){
            for(int x = originX -2; x <= originX + 2; x++){
                for(int y = originY; y <= originY + 2; y++){
                    for(int z = originZ-2;  z <= originZ + 2; z++){
                        Block blockToManipulate = world.getBlockAt(x, y, z);
                        replaceBlockIfAllowed(plugin, blockToManipulate);
                    }
                }
            }
        }

    }

    private static void replaceBlockIfAllowed(BlockClicker plugin, Block block) {
        if (plugin.zoneCache.isAllowedToBeManipulated(block)){
            block.setType(Material.AIR, false);
        }
    }

}
