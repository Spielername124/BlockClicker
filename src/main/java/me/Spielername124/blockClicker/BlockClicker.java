package me.Spielername124.blockClicker;

import BlockBreak.BlockBreakListener;
import BlockBreak.BlockClickerCommands;
import BlockBreak.GlobalFlags;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class BlockClicker extends JavaPlugin {

    private File itemsFile;
    private FileConfiguration itemsConfig;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        createItemsConfig();

        getCommand("blockclicker").setExecutor(new BlockClickerCommands(this));

        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void reloadAllConfigs() {
        reloadConfig();
        itemsConfig = YamlConfiguration.loadConfiguration(itemsFile);
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
            itemsConfig.save(itemsFile);
        } catch (IOException e) {
            getLogger().severe("Could not save items.yml!");
        }
    }

}
