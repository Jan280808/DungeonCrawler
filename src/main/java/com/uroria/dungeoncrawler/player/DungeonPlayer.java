package com.uroria.dungeoncrawler.player;

import org.bukkit.entity.Player;

import java.util.UUID;

public class DungeonPlayer {

    private final Player player;

    public DungeonPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public UUID getUUID() {
        return player.getUniqueId();
    }
}
