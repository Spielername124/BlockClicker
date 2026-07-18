package me.Spielername124.blockClicker.ZoneManagement;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Location;

public class BlockManipulationRegion {
    private final String region;

    public BlockManipulationRegion(String region) {
        this.region = (region.toLowerCase());
    }

    public boolean isInZone(Location location) {
        if (region == null || region.isEmpty()) {
            return false;
        }

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(location.getWorld()));

        if (regions != null) {
            //search the location is in the world guard region
            ProtectedRegion targetRegion = regions.getRegion(region);
            if (targetRegion != null && targetRegion.contains(BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ()))) {
                return true;
            }

        }
        return false;
    }
}
