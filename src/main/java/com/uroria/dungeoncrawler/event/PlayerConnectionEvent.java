package com.uroria.dungeoncrawler.event;

import com.uroria.dungeoncrawler.player.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionEvent implements Listener {

    private final PlayerManager playerManager;

    public PlayerConnectionEvent(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.joinMessage(null);
        playerManager.registerPlayer(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.quitMessage(null);
    }
}
