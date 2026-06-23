package BlockBreak.ToolManagement.Tools;

import BlockBreak.ToolManagement.ToolCache;
import BlockBreak.ToolManagement.ToolGroup;
import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ToolCacheLoader {
    public static void loadAllToolGroups(BlockClicker plugin, FileConfiguration config, ToolCache toolCache) {
        toolCache.clear();

        //get the tool-groups section and then all the groups
        ConfigurationSection toolGroupsSection = config.getConfigurationSection("tool-groups");
        if (toolGroupsSection == null) return;
        for (String groupName : toolGroupsSection.getKeys(false)) {
            ConfigurationSection groupSection = toolGroupsSection.getConfigurationSection(groupName);
            if (groupSection == null) continue;

            ToolGroup toolGroup = new ToolGroup();

            //if the group is always possible, include a AlwaysPossible so that the checks always work
            if (groupSection.getBoolean("always-possible", false)) {
                toolGroup.addTool(new AlwaysPossible());
                continue;
            }

            List<String> toolsList = groupSection.getStringList("tools");

            // iterate trough every tool in the list

            for (String toolEntry : toolsList) {
                //get if the name is one of a standard Minecraft material
                Material material = Material.matchMaterial(toolEntry);

                //if it is a standard item, add it as a normal Item
                if(material != null){
                    toolGroup.addTool(new NormalTool(material));
                }

                else{
                    //add the tool if it is found in the config
                    if (plugin.getItemsConfig().contains("saved-items." + toolEntry)) {
                        toolGroup.addTool(new CustomTool(toolEntry));
                    } else {
                        //give out a warning that the tool doesn't exist
                        plugin.getLogger().warning("[Config Error] Tool entry '" + toolEntry + "' in group '" + groupName + "' is neither a valid Material nor a registered Custom Item ID.");
                    }
                }
            }
            //register the group to the cache
            toolCache.registerToolGroup(groupName, toolGroup);
        }
    }
}
