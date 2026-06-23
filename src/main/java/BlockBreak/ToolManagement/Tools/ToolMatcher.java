package BlockBreak.ToolManagement.Tools;

import org.bukkit.inventory.ItemStack;

public interface ToolMatcher {
    boolean matchTool(ItemStack toolUsed);
}
