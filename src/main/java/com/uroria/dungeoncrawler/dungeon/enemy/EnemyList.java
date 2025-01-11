package com.uroria.dungeoncrawler.dungeon.enemy;

import org.bukkit.entity.EntityType;

public enum EnemyList {
    PILLAGER(1, "Pillager", 40, 1, EntityType.PILLAGER);

    private final int id;
    private final String displayName;
    private final double health;
    private final double damage;
    private final EntityType entityType;

    EnemyList(int id, String displayName, double health, double damage, EntityType entityType) {
        this.id = id;
        this.displayName = displayName;
        this.health = health;
        this.damage = damage;
        this.entityType = entityType;
    }

    public int getId() {
        return id;
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

    public EntityType getEntityType() {
        return entityType;
    }
}
