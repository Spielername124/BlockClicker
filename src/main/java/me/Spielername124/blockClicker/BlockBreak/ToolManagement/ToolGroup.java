package me.Spielername124.blockClicker.BlockBreak.ToolManagement;

import me.Spielername124.blockClicker.BlockBreak.ToolManagement.Tools.ToolMatcher;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ToolGroup {
    private final List<ToolMatcher> savedTools = new ArrayList<>();

    public void addTool(ToolMatcher toolMatcher){
        if(toolMatcher != null)
            savedTools.add(toolMatcher);
    }

    public boolean isToolAllowed(ItemStack toolUsed){
        //try every saved tool in this tool group
        for(ToolMatcher savedTool : savedTools){
            if (savedTool.matchTool(toolUsed)) return true;
        }
        return false;
    }
}
