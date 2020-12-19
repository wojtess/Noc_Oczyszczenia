package me.wojtess.noc_oczyszczenia;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Noc_Oczyszczenia extends JavaPlugin {

    public static Noc_Oczyszczenia INSTANCE;
    public boolean isNight = false;
    private int task_id;
    private BossBar bar;
    public FileConfiguration config;

    @Override
    public void onEnable() {
        try {
            Noc_Oczyszczenia.INSTANCE = this;
            isNight = false;
            for(World world:Bukkit.getWorlds()) {
                world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE,false);
                world.setTime(0);
            }
            this.bar = Utils.createBossBar("noc", BarColor.RED);
            task_id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Noc_Oczyszczenia.INSTANCE,new Task(this.bar),0,20);
            Bukkit.getServer().getPluginManager().registerEvents(new Event(),Noc_Oczyszczenia.INSTANCE);
            onEnableConfig();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void onEnableConfig() throws IOException, InvalidConfigurationException {
        File configFile = new File(getDataFolder(),"config.yml");
        configFile.getParentFile().mkdirs();
        if(!configFile.exists()) {
            this.getConfig().save(configFile);
        }
        config.load(configFile);
        Bukkit.broadcastMessage(config.saveToString());
    }

    @Override
    public void onDisable() {
        Noc_Oczyszczenia.INSTANCE = null;
        Bukkit.getServer().getScheduler().cancelTask(task_id);
        this.bar.setVisible(false);
        this.bar.removeAll();
    }
}
