package BlockBreak;

import BlockBreak.RewardManagement.RewardCache;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    private final BlockClicker plugin;
    private final RewardCache rewardCache;


    public BlockBreakListener (BlockClicker plugin, RewardCache rewardCache){
        this.plugin = plugin;
        this.rewardCache = rewardCache;
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent brokenBlock){
        //geting the Block and it's location
        Block block = brokenBlock.getBlock();
        Location location = block.getLocation();

        //get the config
        FileConfiguration config = plugin.getConfig();

        Player player =  brokenBlock.getPlayer();

        BlockBreakHandler.checkAreas(plugin, config, rewardCache, player, block, location);
    }
}
