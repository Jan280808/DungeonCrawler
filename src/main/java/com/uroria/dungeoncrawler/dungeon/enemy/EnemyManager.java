package com.uroria.dungeoncrawler.dungeon.enemy;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.*;

public class EnemyManager {

    private final Map<Integer, Enemy> livingEnemyMap;

    public EnemyManager() {
        this.livingEnemyMap = new HashMap<>();
    }

    @Deprecated
    public void spawnEnemy(EnemyList enemyList, Location location) {
        registerLivingEnemy(new Enemy(enemyList.getDisplayName(), enemyList.getHealth(), enemyList.getDamage()).spawn(enemyList.getEntityType(), location, false));
    }

    private void registerLivingEnemy(Enemy enemy) {
        if(enemy == null) return;
        livingEnemyMap.put(enemy.getEntity().getEntityId(), enemy);
    }

    public void unregisterLivingEnemy(Enemy enemy) {
        if(enemy == null) return;
        livingEnemyMap.remove(enemy.getEntity().getEntityId());
    }

    public Enemy getLivingEnemy(Entity entity) {
        try {
            return livingEnemyMap.get(entity.getEntityId());
        } catch (Exception exception) {
            System.out.print("there is no livingEnemy with the id " + entity.getEntityId() + "in the cache");
            exception.fillInStackTrace();
            return null;
        }
    }

    public boolean isRegistered(Entity entity) {
        return livingEnemyMap.containsKey(entity.getEntityId());
    }
}
