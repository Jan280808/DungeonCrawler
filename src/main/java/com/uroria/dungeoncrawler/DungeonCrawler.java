package com.uroria.dungeoncrawler;

import com.uroria.dungeoncrawler.command.CreateDungeonCommand;
import com.uroria.dungeoncrawler.command.DeleteDungeonCommand;
import com.uroria.dungeoncrawler.command.TeleportPlayerCommand;
import com.uroria.dungeoncrawler.dungeon.DungeonManager;
import com.uroria.dungeoncrawler.event.DisableEvents;
import com.uroria.dungeoncrawler.event.PlayerAttackEnemyEvent;
import com.uroria.dungeoncrawler.event.PlayerConnectionEvent;
import com.uroria.dungeoncrawler.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class DungeonCrawler extends JavaPlugin {

    public static DungeonCrawler instance;
    private PlayerManager playerManager;
    private DungeonManager dungeonManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        this.playerManager = new PlayerManager();
        this.dungeonManager = new DungeonManager();
        registerListener(Bukkit.getPluginManager());
        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        dungeonManager.getGenerator().deleteAllWorld();
    }

    private void registerListener(PluginManager pluginManager) {
        //pluginManager.registerEvents(new PlayerInteractionEvent(), this);
        pluginManager.registerEvents(new DisableEvents(), this);
        pluginManager.registerEvents(new PlayerAttackEnemyEvent(null), this);
        pluginManager.registerEvents(new PlayerConnectionEvent(playerManager), this);
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("generate")).setExecutor(new CreateDungeonCommand(dungeonManager));
        Objects.requireNonNull(getCommand("delete")).setExecutor(new DeleteDungeonCommand(dungeonManager));
        Objects.requireNonNull(getCommand("structure")).setExecutor(new TeleportPlayerCommand(dungeonManager));
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public DungeonManager getDungeonManager() {
        return dungeonManager;
    }
}
