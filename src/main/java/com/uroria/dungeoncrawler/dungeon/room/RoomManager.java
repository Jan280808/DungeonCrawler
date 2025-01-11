package com.uroria.dungeoncrawler.dungeon.room;

import com.uroria.dungeoncrawler.DungeonCrawler;
import com.uroria.dungeoncrawler.dungeon.DungeonType;
import com.uroria.dungeoncrawler.util.Cube;
import com.uroria.dungeoncrawler.util.FileBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class RoomManager {

    private final DungeonType dungeonType;
    private final Map<Integer, Room> roomMap;
    private final FileBuilder fileBuilder;

    public RoomManager(DungeonType dungeonType) {
        this.dungeonType = dungeonType;
        this.roomMap = new HashMap<>();
        this.fileBuilder = new FileBuilder("room");
        loadRoomCache();
        showRoom();
    }

    private void showRoom() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(DungeonCrawler.instance, () -> roomMap.forEach((integer, room) -> {
            Cube cube = room.getCube();
            cube.showCube(Particle.VILLAGER_HAPPY);
        }), 0, 20);
    }

    private void loadRoomCache() {
        fileBuilder.getStrings(false).forEach(s -> {
            int id = Integer.parseInt(fileBuilder.getString(s + ".id"));
            RoomType roomType = RoomType.valueOf(fileBuilder.getString(s + ".roomType"));
            Location l1 = fileBuilder.getLocation(s + ".l1");
            Location l2 = fileBuilder.getLocation(s + ".l2");
            Location spawn = fileBuilder.getLocation(s + ".spawn");
            roomMap.put(id, new Room(id, roomType, l1, l2, spawn));
            System.out.print("room " + id + " was loaded");
        });
    }

    public void setupRoom(Player player, int id, RoomType roomType, Location l1, Location l2, Location spawn) {
        String path = "room"+id;
        fileBuilder.setString(path + ".id", String.valueOf(id));
        fileBuilder.setString(path + ".roomType", roomType.toString());
        fileBuilder.setLocation(path + ".l1", l1);
        fileBuilder.setLocation(path + ".l2", l2);
        fileBuilder.setLocation(path + ".spawn", spawn);
        player.sendMessage("room: " + id + " with type: " + roomType + " was created");
    }

    public Room getRoom(int id) {
        try {
            return roomMap.get(id);
        } catch (Exception exception) {
            System.out.print("there is no room with the id " + id + " in the cache");
            exception.fillInStackTrace();
            return null;
        }
    }

    public Map<Integer, Room> getRoomMap() {
        return roomMap;
    }
}
