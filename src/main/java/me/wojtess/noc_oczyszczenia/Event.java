package me.wojtess.noc_oczyszczenia;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Event implements Listener {

    @EventHandler
    public void onDamageByPlayer(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            if(!Noc_Oczyszczenia.INSTANCE.isNight) {
                event.setCancelled(true);
            }
        }
    }

}
