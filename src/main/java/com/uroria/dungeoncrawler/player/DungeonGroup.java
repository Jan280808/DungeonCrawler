package com.uroria.dungeoncrawler.player;

import com.google.common.annotations.Beta;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DungeonGroup {

    private final Map<UUID, DungeonPlayer> playerMap;

    public DungeonGroup() {
        this.playerMap = new HashMap<>();
    }

    @Beta
    public void joinGroup(Player player) {
        playerMap.put(player.getUniqueId(), new DungeonPlayer(player));
    }

    public void quitGroup(Player player) {
        playerMap.remove(player.getUniqueId());
    }

    public DungeonPlayer getDungeonPlayer(Player player) {
        return playerMap.get(player.getUniqueId());
    }

    public Map<UUID, DungeonPlayer> getPlayerMap() {
        return playerMap;
    }

    public boolean isMember(Player player) {
        return playerMap.containsKey(player.getUniqueId());
    }

    public boolean isFull() {
        return playerMap.size() == 4;
    }

    public int groupAmount() {
        return playerMap.size();
    }
}
