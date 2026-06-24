package BlockBreak.ZoneManagement;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Location;

import java.util.List;

public class ZoneGroup {
    private final String name;
    private final boolean everywhere;
    private final List<String> regionIds;

    public String getName() {
        return name;
    }

    public ZoneGroup(String name, boolean everywhere, List<String> regionIds){
        this.name = name;
        this.everywhere = everywhere;
        this.regionIds = regionIds;
    }

    public boolean isInZone(Location location) {
        if (everywhere) {
            return true;
        }

        if (regionIds == null || regionIds.isEmpty()) {
            return false;
        }

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(location.getWorld()));

        if (regions != null) {
            //search the location is in one of the provided WorldGuard regions
            for (String id : regionIds) {
                ProtectedRegion targetRegion = regions.getRegion(id);
                if (targetRegion != null && targetRegion.contains(BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ()))) {
                    return true;
                }
            }
        }
        return false;
    }
}
