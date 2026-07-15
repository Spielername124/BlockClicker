package me.Spielername124.blockClicker.BlockBreak.ZoneManagement;

import java.util.ArrayList;
import java.util.List;

public class ZoneCache {
    private final List<ZoneGroup> cachedZoneGroups = new ArrayList<>();

    public void clear() {
        cachedZoneGroups.clear();
    }

    public void registerZoneGroup(ZoneGroup zoneGroup) {
        cachedZoneGroups.add(zoneGroup);
    }

    public List<ZoneGroup> getCachedZoneGroups() {
        return cachedZoneGroups;
    }
}
