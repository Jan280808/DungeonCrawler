package com.uroria.dungeoncrawler.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class DisableEvents implements Listener {

    @EventHandler
    public void naturalSpawnEntity(CreatureSpawnEvent event) {
        CreatureSpawnEvent.SpawnReason spawnReason = event.getSpawnReason();
        if(spawnReason.equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) {
            event.setCancelled(false);
        } else event.setCancelled(!spawnReason.equals(CreatureSpawnEvent.SpawnReason.COMMAND));
    }
}
