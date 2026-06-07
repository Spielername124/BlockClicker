package me.Spielername124.blockClicker;

import BlockBreak.BlockBreakListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlockClicker extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
