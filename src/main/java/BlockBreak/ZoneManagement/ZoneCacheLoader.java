package BlockBreak.ZoneManagement;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ZoneCacheLoader {

    public static void loadAllZoneGroups(FileConfiguration config, ZoneCache zoneCache) {
        zoneCache.clear();

        ConfigurationSection zoneGroupsSection = config.getConfigurationSection("zone-groups");
        if (zoneGroupsSection == null) return;

        //for each of the zoneGroups, register them and their regions
        for (String zoneGroupName : zoneGroupsSection.getKeys(false)) {
            ConfigurationSection zoneGroupSection = zoneGroupsSection.getConfigurationSection(zoneGroupName);
            if (zoneGroupSection == null) continue;

            boolean everywhere = zoneGroupSection.getBoolean("everywhere", false);
            List<String> regionIds = zoneGroupSection.getStringList("region-ids");

            ZoneGroup zoneGroup = new ZoneGroup(zoneGroupName, everywhere, regionIds);
            zoneCache.registerZoneGroup(zoneGroup);
        }
    }
}
