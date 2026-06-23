package me.Spielername124.blockClicker;

import BlockBreak.BlockBreakListener;
import BlockBreak.BlockClickerCommands;
import BlockBreak.GlobalFlags;
import BlockBreak.RewardManagement.RewardCache;
import BlockBreak.RewardManagement.RewardCacheLoader;
import BlockBreak.ToolManagement.ToolCache;
import BlockBreak.ToolManagement.Tools.ToolCacheLoader;
import BlockBreak.ToolManagement.Tools.ToolMatcher;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class BlockClicker extends JavaPlugin {

    private File itemsFile;
    private FileConfiguration itemsConfig;
    private RewardCache rewardCache;
    private ToolCache toolCache;
    public static NamespacedKey TOOL_ID_KEY;


    @Override
    public void onEnable() {
        saveDefaultConfig();
        createItemsConfig();

        //create the rewardCache
        this.rewardCache = new RewardCache();
        RewardCacheLoader.loadAllLootTables(this, getConfig(), rewardCache);

        //create the toolCache
        this.toolCache = new ToolCache();
        ToolCacheLoader.loadAllToolGroups(this, getConfig(), toolCache);

        getCommand("blockclicker").setExecutor(new BlockClickerCommands(this));

        getServer().getPluginManager().registerEvents(new BlockBreakListener(this, rewardCache, toolCache), this);

    }

    @Override
    public void onDisable() {
    }

    public void reloadAllConfigs() {
        //reload configs and everything that depends on it (the caches)
        reloadConfig();
        itemsConfig = YamlConfiguration.loadConfiguration(itemsFile);
        RewardCacheLoader.loadAllLootTables(this, getConfig(), rewardCache);
        ToolCacheLoader.loadAllToolGroups(this, getConfig(), toolCache);
    }






    private void createItemsConfig(){
        itemsFile = new File(getDataFolder(), "items.yml");
        if (!itemsFile.exists()) {
            itemsFile.getParentFile().mkdirs();
            try {
                itemsFile.createNewFile();
            } catch (IOException e) {
                getLogger().severe("Could not create items.yml!");
                e.printStackTrace();
            }
        }
        itemsConfig = YamlConfiguration.loadConfiguration(itemsFile);
    }

    public FileConfiguration getItemsConfig() {
        return itemsConfig;
    }

    public void saveItemsConfig() {
        try {
            //update the ItemConfig and reload all config to make the changes working
            itemsConfig.save(itemsFile);
            reloadAllConfigs();
        } catch (IOException e) {
            getLogger().severe("Could not save items.yml!");
        }
    }

}
