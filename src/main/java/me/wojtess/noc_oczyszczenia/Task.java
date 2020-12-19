package me.wojtess.noc_oczyszczenia;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;

import java.util.Set;

public class Task implements Runnable {
    private int counter = 0;
    private BossBar bar;

    public Task(BossBar bar) {
        this.bar = bar;
    }

    @Override
    public void run() {
        this.counter++;
        int time_day = Noc_Oczyszczenia.INSTANCE.config.getInt("time.day");
        int time_night = Noc_Oczyszczenia.INSTANCE.config.getInt("time.night");
        if(!Noc_Oczyszczenia.INSTANCE.isNight && this.counter > time_day) {
            this.counter = 0;
            Noc_Oczyszczenia.INSTANCE.isNight = true;
            String message = Noc_Oczyszczenia.INSTANCE.config.getString("messages.start_night");
            if(message != null && !(message.equals("") || message.equals(" "))) {
                Bukkit.broadcastMessage(Utils.color(message));
            }
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.getWorld().strikeLightningEffect(player.getTargetBlock((Set<Material>) null,200).getLocation());
            });
            this.bar.setVisible(true);
            Bukkit.getOnlinePlayers().forEach(bar::addPlayer);
        }
        if(Noc_Oczyszczenia.INSTANCE.isNight && this.counter > time_night) {
            this.counter = 0;
            Noc_Oczyszczenia.INSTANCE.isNight = false;
            String message = Noc_Oczyszczenia.INSTANCE.config.getString("messages.end_night");
            if(message != null && !(message.equals("") || message.equals(" "))) {
                Bukkit.broadcastMessage(Utils.color(message));
            }

            this.bar.setVisible(false);
        }
        int precent = (this.counter * 12000);
        for(World world:Bukkit.getWorlds()) {
            if(!Noc_Oczyszczenia.INSTANCE.isNight) {
                world.setTime((precent / time_day));
            } else {
                world.setTime(12000 + (precent / time_night));
            }
        }
        if(Noc_Oczyszczenia.INSTANCE.isNight) {
            this.bar.setProgress(1.0 - ((float)this.counter / (float)time_night));
        }
    }
}
