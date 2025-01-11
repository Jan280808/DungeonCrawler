package com.uroria.dungeoncrawler.dungeon;

import com.uroria.dungeoncrawler.dungeon.room.Room;
import com.uroria.dungeoncrawler.dungeon.room.RoomManager;
import com.uroria.dungeoncrawler.player.DungeonGroup;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Dungeon {

    private final int id;
    private final DungeonType dungeonType;

    private final DungeonGroup dungeonGroup;
    private final RoomManager roomManager;
    private final List<Room> roomList;
    private boolean isGenerated;
    private Room currentRoom;

    public Dungeon(int id, DungeonType dungeonType) {
        this.id = id;
        this.dungeonType = dungeonType;

        this.dungeonGroup = new DungeonGroup();
        this.roomManager = new RoomManager(dungeonType);
        this.roomList = new ArrayList<>();
    }

    public void playerJoin(Player player) {
        if(dungeonGroup.isFull()) {
            player.sendMessage("dungeon is full");
            return;
        }
        dungeonGroup.joinGroup(player);
        player.sendMessage("join dungeon id: " + id);
    }

    public void playerQuit(Player player) {
        if(!dungeonGroup.isMember(player)) return;
        dungeonGroup.quitGroup(player);
    }

    public void start() {

    }

    public void stop() {

    }

    private void loadRooms() {

    }
}
