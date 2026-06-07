package BlockBreak;

import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class toolIsAllowedCheck {
    public static boolean checkTool(FileConfiguration config, ItemStack toolUsed, String groupName){
        ConfigurationSection group = config.getConfigurationSection("tool-Groups." + groupName);
        if (group == null) return false;

        //returns true if the pooled group has the flag to be always active
        if (group.getBoolean("alwaysPossible", false)) return true;

        //gets the list of allowed tools and returns if the used tool is contained in it.
        List<String> allowedTools = group.getStringList("tools");
        return allowedTools.contains(toolUsed.getType().name());
    }
}
