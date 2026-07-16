package me.Spielername124.blockClicker;

import me.Spielername124.blockClicker.BlockBreak.BlockBreak.BlockBreakListener;
import me.Spielername124.blockClicker.BlockBreak.CommandManagement.BlockClickerCommands;
import me.Spielername124.blockClicker.BlockBreak.GlobalFlags;
import me.Spielername124.blockClicker.BlockBreak.MobDeath.MobDeathListener;
import me.Spielername124.blockClicker.BlockBreak.MobDeath.MobLootCache;
import me.Spielername124.blockClicker.BlockBreak.MobDeath.MobLootCacheLoader;
import me.Spielername124.blockClicker.BlockBreak.RewardManagement.RewardCache;
import me.Spielername124.blockClicker.BlockBreak.RewardManagement.RewardCacheLoader;
import me.Spielername124.blockClicker.BlockBreak.ToolManagement.ToolCache;
import me.Spielername124.blockClicker.BlockBreak.ToolManagement.Tools.ToolCacheLoader;
import me.Spielername124.blockClicker.BlockBreak.ZoneManagement.ZoneCache;
import me.Spielername124.blockClicker.BlockBreak.ZoneManagement.ZoneCacheLoader;
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
    public ZoneCache zoneCache;
    private MobLootCache mobLootCache;

    public GlobalFlags flags;
    public static NamespacedKey TOOL_ID_KEY;
    public static NamespacedKey MOB_ID_KEY;


    @Override
    public void onEnable() {

        TOOL_ID_KEY = new NamespacedKey(this, "custom_tool_id");
        MOB_ID_KEY = new NamespacedKey(this, "custom_mob_id");

        saveDefaultConfig();
        createItemsConfig();

        //create the rewardCache
        this.rewardCache = new RewardCache();
        RewardCacheLoader.loadAllLootTables(this, getConfig(), rewardCache);

        //create the toolCache
        this.toolCache = new ToolCache();
        ToolCacheLoader.loadAllToolGroups(this, getConfig(), toolCache);

        this.zoneCache = new ZoneCache();
        ZoneCacheLoader.loadAllZoneGroups(getConfig(), zoneCache);

        this.mobLootCache = new MobLootCache();
        MobLootCacheLoader.LoadAllMobLootTables(this, getConfig(), mobLootCache);

        this.flags = new GlobalFlags(getConfig());

        getCommand("blockclicker").setExecutor(new BlockClickerCommands(this));

        getServer().getPluginManager().registerEvents(new BlockBreakListener(this, rewardCache, toolCache, zoneCache), this);
        getServer().getPluginManager().registerEvents(new MobDeathListener(this, mobLootCache ), this);

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
        ZoneCacheLoader.loadAllZoneGroups (getConfig(), zoneCache);
        MobLootCacheLoader.LoadAllMobLootTables(this, getConfig(), mobLootCache);
        flags.update(getConfig());
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
