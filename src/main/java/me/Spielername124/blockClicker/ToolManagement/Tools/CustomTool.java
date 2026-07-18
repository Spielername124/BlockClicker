package me.Spielername124.blockClicker.ToolManagement.Tools;

import me.Spielername124.blockClicker.BlockClicker;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class CustomTool implements ToolMatcher {
    private final String toolId;

    public CustomTool(String expectedToolId) {
        this.toolId = expectedToolId;
    }

    @Override
    public boolean matchTool(ItemStack toolUsed){
        //get the meta, if none exists, return false
        if (toolUsed == null || !toolUsed.hasItemMeta()) {
            return false;
        }
        ItemMeta meta = toolUsed.getItemMeta();
        if (meta == null) {
            return false;
        }

        //if the tool contains the same id, return true
        String usedToolId = meta.getPersistentDataContainer().get(BlockClicker.TOOL_ID_KEY, PersistentDataType.STRING);
        return toolId.equals(usedToolId);

    }
}
