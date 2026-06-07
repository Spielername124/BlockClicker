package BlockBreak;

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
    private final FileConfiguration config;

    public BlockBreakListener (BlockClicker plugin){
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent brokenBlock){
        //geting the Block and it's location
        Block block = brokenBlock.getBlock();
        Location location = block.getLocation();


        //polling the config
        String ClickerArea = config.getString("protected-zone.region-id");
        if (ClickerArea == null) return;

        //get the Zone from Worldguard (if it Exists)
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(location.getWorld()));
        if (regions != null) {
            ProtectedRegion targetRegion = regions.getRegion(ClickerArea);
            if (targetRegion != null && targetRegion.contains(BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ()))) {

                //call the handler for the event
                Player player =  brokenBlock.getPlayer();
                BlockBreakHandler.onBlockBreakInZone(plugin, config, player, block, location);

            }
        }
    }
}
