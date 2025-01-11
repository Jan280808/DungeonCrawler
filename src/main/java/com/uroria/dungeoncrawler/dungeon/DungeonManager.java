package com.uroria.dungeoncrawler.dungeon;

import com.google.common.annotations.Beta;
import com.uroria.dungeoncrawler.dungeon.generator.Generator;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class DungeonManager {

    private final Map<Integer, Dungeon> dungeonMap;
    private final Generator generator;

    public DungeonManager() {
        this.dungeonMap = new HashMap<>();
        this.generator = new Generator();
    }

    @Beta
    public void startDungeon(int id, Player player) {
        Dungeon dungeon = new Dungeon(id, DungeonType.TREE);
        dungeon.playerJoin(player);
        dungeon.start();
    }

    public Map<Integer, Dungeon> getDungeonMap() {
        return dungeonMap;
    }

    public Generator getGenerator() {
        return generator;
    }
}
