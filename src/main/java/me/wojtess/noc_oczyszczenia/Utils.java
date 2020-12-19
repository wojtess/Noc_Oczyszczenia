package me.wojtess.noc_oczyszczenia;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

public class Utils {

    public static BossBar createBossBar(String title, BarColor color) {
        BossBar bar = Bukkit.getServer().createBossBar(title,color,BarStyle.SOLID);
        bar.setProgress(0);
        bar.setVisible(true);
        return bar;
    }

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&',message);
    }

}
