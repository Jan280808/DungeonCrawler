package com.uroria.dungeoncrawler.dungeon.enemy;

import org.bukkit.Location;
import org.bukkit.entity.*;

public class Enemy {

    private final String displayName;
    private final double health, damage;
    private TextDisplay display;
    private Entity entity;

    public Enemy(String displayName, double health, double damage) {
        this.displayName = displayName;
        this.health = health;
        this.damage = damage;
    }

    @Deprecated
    public Enemy spawn(EntityType entityType, Location location, boolean aiActivate) {
        entity = location.getWorld().spawnEntity(location.add(0.5,0,0.5), entityType);
        LivingEntity livingEntity = (LivingEntity) entity;
        livingEntity.setAI(aiActivate);

        //setup enemy
        livingEntity.setMaxHealth(Double.MAX_VALUE);
        livingEntity.setHealth(health);
        livingEntity.setSilent(true);
        livingEntity.setCanPickupItems(false);

        //display the name of the enemy
        display = location.getWorld().spawn(location, TextDisplay.class);
        display.setBillboard(Display.Billboard.CENTER);
        display.setText(displayName + "\n health: " + health);
        entity.setPassenger(display);
        return this;
    }

    public void death() {
        display.remove();
        entity.remove();
    }

    @Deprecated
    public void updateHealthDisplay(double currentHealth) {
        display.setText(displayName + "\n health: " + currentHealth);
    }

    public int getEntityId() {
        return entity.getEntityId();
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getHealth() {
        return health;
    }

    public double getDamage() {
        return damage;
    }

    public Entity getEntity() {
        return entity;
    }
}
