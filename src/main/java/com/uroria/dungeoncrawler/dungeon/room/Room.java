package com.uroria.dungeoncrawler.dungeon.room;

import com.uroria.dungeoncrawler.dungeon.enemy.EnemyList;
import com.uroria.dungeoncrawler.dungeon.enemy.EnemyManager;
import com.uroria.dungeoncrawler.util.Cube;
import org.bukkit.Location;
import org.bukkit.Material;

public class Room {

    private final int id;
    private final RoomType roomType;
    private final Location l1, l2, playerSpawn;
    private final Cube cube;
    private final EnemyManager enemyManager;

    public Room(int id, RoomType roomType, Location l1, Location l2, Location playerSpawn) {
        this.id = id;
        this.roomType = roomType;
        this.l1 = l1;
        this.l2 = l2;
        this.playerSpawn = playerSpawn;
        this.cube = new Cube(l1, l2);
        this.enemyManager = new EnemyManager();
        spawnEnemy();
    }

    public void spawnEnemy() {
        cube.blockList().forEach(block -> {
            if(block.getType().equals(Material.LODESTONE)) {
                Location location = block.getLocation().clone().add(0, 1, 0);
                enemyManager.spawnEnemy(EnemyList.PILLAGER, location);
            }
        });
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public int getId() {
        return id;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public Location getL1() {
        return l1;
    }

    public Location getL2() {
        return l2;
    }

    public Location getPlayerSpawn() {
        return playerSpawn;
    }

    public Cube getCube() {
        return cube;
    }
}
