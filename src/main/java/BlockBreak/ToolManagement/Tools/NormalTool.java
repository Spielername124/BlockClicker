package BlockBreak.ToolManagement.Tools;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NormalTool implements ToolMatcher {
    private final Material material;

    public NormalTool(Material material){
        this.material = material;
    }

    @Override
    public boolean matchTool(ItemStack toolUsed){
        //returns if the material of the tool used is the same as the saved one
        return toolUsed != null && toolUsed.getType() == material;
    }
}
