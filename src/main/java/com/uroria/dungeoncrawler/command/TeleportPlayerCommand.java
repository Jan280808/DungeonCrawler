package com.uroria.dungeoncrawler.command;

import com.uroria.dungeoncrawler.dungeon.DungeonManager;
import com.uroria.dungeoncrawler.dungeon.generator.StructureManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TeleportPlayerCommand implements TabExecutor {

    private final StructureManager structureManager;

    public TeleportPlayerCommand(DungeonManager dungeonManager) {
        this.structureManager = dungeonManager.getGenerator().getStructureManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) sender;
        structureManager.teleportPlayer(player);
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
