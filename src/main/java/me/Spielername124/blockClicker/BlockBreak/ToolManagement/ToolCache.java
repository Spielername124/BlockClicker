package me.Spielername124.blockClicker.BlockBreak.ToolManagement;

import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ToolCache {
    private final Map<String, ToolGroup> cachedGroups = new LinkedHashMap<>();

    public void clear() {
        cachedGroups.clear();
    }

    public void registerToolGroup(String groupName, ToolGroup group) {
        cachedGroups.put(groupName, group);
    }

    public boolean isToolAllowed(String groupName, ItemStack toolUsed) {
        ToolGroup group = cachedGroups.get(groupName);
        return group != null && group.isToolAllowed(toolUsed);
    }

    public Set<String> getAllToolGroups() {
        return cachedGroups.keySet();
    }
}

