package com.uroria.dungeoncrawler.event;

import com.uroria.dungeoncrawler.DungeonCrawler;
import com.uroria.dungeoncrawler.dungeon.room.RoomManager;
import com.uroria.dungeoncrawler.dungeon.room.RoomType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractionEvent implements Listener {

    private RoomManager roomManager;

    public PlayerInteractionEvent() {

    }

    private Location l1, l2, spawn;

    @EventHandler
    public void interaction(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack cursorItem = event.getItem();

        if(event.getClickedBlock() == null) return;

        if(cursorItem.getType().equals(Material.STICK)) {
            l1 = event.getClickedBlock().getLocation();
            player.sendMessage("l1 setzt");
        }
        if(cursorItem.getType().equals(Material.BLAZE_ROD)) {
            l2 = event.getClickedBlock().getLocation();
            player.sendMessage("l2 setzt");
        }
        if(cursorItem.getType().equals(Material.EGG)) {
            spawn = event.getClickedBlock().getLocation();
            player.sendMessage("spawn setzt");
        }

        if(cursorItem.getType().equals(Material.GRASS_BLOCK)) {
            roomManager.setupRoom(player, 1, RoomType.KILL, l1, l2, spawn);
        }
    }
}
