package me.Spielername124.blockClicker.BlockBreak.ToolManagement.Tools;

import org.bukkit.inventory.ItemStack;

public class AlwaysPossible implements ToolMatcher {
    @Override
    public boolean matchTool(ItemStack toolUsed) {return true;}
}
