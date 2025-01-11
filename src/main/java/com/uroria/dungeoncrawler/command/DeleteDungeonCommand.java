package com.uroria.dungeoncrawler.command;

import com.uroria.dungeoncrawler.dungeon.DungeonManager;
import com.uroria.dungeoncrawler.dungeon.generator.Generator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DeleteDungeonCommand implements TabExecutor {

    private final Generator generator;

    public DeleteDungeonCommand(DungeonManager dungeonManager) {
        this.generator = dungeonManager.getGenerator();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(sender instanceof ConsoleCommandSender) {
            sender.sendMessage("you must be a player");
            return false;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("XXX")) return false;
        if(strings.length == 0) {
            player.sendMessage("usw: /generate <id>");
            return false;
        }
        if(strings.length == 1) {
            try {
                int id = Integer.parseInt(strings[0]);
                generator.deleteWorld(id);
                player.sendMessage("dungeonWorld with id " + id + " was deleted");
                return true;

            } catch (Exception exception) {
                player.sendMessage("the id must be an integer");
                return false;
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
