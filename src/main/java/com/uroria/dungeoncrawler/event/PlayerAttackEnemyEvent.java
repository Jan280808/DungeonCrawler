package com.uroria.dungeoncrawler.event;

import com.uroria.dungeoncrawler.dungeon.enemy.Enemy;
import com.uroria.dungeoncrawler.dungeon.enemy.EnemyManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class PlayerAttackEnemyEvent implements Listener {

    private final EnemyManager enemyManager;

    public PlayerAttackEnemyEvent(EnemyManager enemyManager) {
        this.enemyManager = enemyManager;
    }

    @EventHandler @Deprecated
    public void playerDamage(EntityDamageByEntityEvent event) {
        if(!event.getDamager().getType().equals(EntityType.PLAYER)) return;
        Player player = (Player) event.getDamager();
        LivingEntity entity = (LivingEntity) event.getEntity();

        player.sendMessage("Entity: " + entity.getEntityId());
        player.sendMessage("isRegistered: " + enemyManager.isRegistered(entity));

        if(!enemyManager.isRegistered(entity)) return;
        Enemy enemy = enemyManager.getLivingEnemy(entity);
        enemy.updateHealthDisplay(entity.getHealth());
        /*
                if(entity.getPassenger() == null) return;
        Entity passenger = entity.getPassenger();
        if(!passenger.getType().equals(EntityType.TEXT_DISPLAY)) return;
         */
    }

    @EventHandler
    public void enemyDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if(!enemyManager.isRegistered(entity)) return;
        Enemy enemy = enemyManager.getLivingEnemy(entity);
        enemy.death();
        enemyManager.unregisterLivingEnemy(enemy);
        event.setCancelled(true);
    }
}
