package me.Spielername124.blockClicker.ZoneManagement;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class ZoneCache {
    private final List<ZoneGroup> cachedZoneGroups = new ArrayList<>();
    private final List<BlockManipulationRegion> cachedBlockManipulationRegions = new ArrayList<>();

    public void clear() {
        cachedZoneGroups.clear();
        cachedBlockManipulationRegions.clear();
    }

    public void registerZoneGroup(ZoneGroup zoneGroup) {
        cachedZoneGroups.add(zoneGroup);
    }

    public List<ZoneGroup> getCachedZoneGroups() {
        return cachedZoneGroups;
    }

    public void registerBlockManipulationRegion(BlockManipulationRegion blockManipulationRegion) {cachedBlockManipulationRegions.add(blockManipulationRegion);}

    //checks if the location is allowed to be manipulated by cycling through the allowed areas
    public boolean isAllowedToBeManipulated(Location location) {
        for (BlockManipulationRegion region : cachedBlockManipulationRegions) {
            if (region.isInZone(location)) return true;
        }
        return false;
    }

    public boolean isAllowedToBeManipulated(Block block) {return isAllowedToBeManipulated(block.getLocation());}

}
