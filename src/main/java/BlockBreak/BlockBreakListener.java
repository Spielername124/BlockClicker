package BlockBreak;

import BlockBreak.RewardManagement.RewardCache;
import BlockBreak.ToolManagement.ToolCache;
import BlockBreak.ZoneManagement.ZoneCache;
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
    private final ToolCache toolCache;
    private final ZoneCache zoneCache;


    public BlockBreakListener (BlockClicker plugin, RewardCache rewardCache, ToolCache toolCache, ZoneCache zoneCache){
        this.plugin = plugin;
        this.rewardCache = rewardCache;
        this.toolCache = toolCache;
        this.zoneCache = zoneCache;
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent brokenBlock){
        //getting the Block and it's location
        Block block = brokenBlock.getBlock();
        Location location = block.getLocation();

        //get the config
        FileConfiguration config = plugin.getConfig();

        Player player =  brokenBlock.getPlayer();
        GlobalFlags flags = plugin.flags;

        BlockBreakHandler.checkAreas(plugin, config, flags, rewardCache, toolCache, zoneCache, player, block, location);
    }
}
