package com.uroria.dungeoncrawler.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {

    private final Map<UUID, DungeonPlayer> dungeonPlayerMap;

    public PlayerManager() {
        this.dungeonPlayerMap = new HashMap<>();
    }

    public void registerPlayer(Player player) {
        if(!dungeonPlayerMap.containsKey(player.getUniqueId())) return;
        dungeonPlayerMap.put(player.getUniqueId(), new DungeonPlayer(player));
    }

    public DungeonPlayer getDungeonPlayer(Player player) {
        return dungeonPlayerMap.get(player.getUniqueId());
    }

    public Map<UUID, DungeonPlayer> getDungeonPlayerMap() {
        return dungeonPlayerMap;
    }
}
