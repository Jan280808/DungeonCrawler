package com.uroria.dungeoncrawler.dungeon.generator;

import com.google.common.annotations.Beta;
import com.uroria.dungeoncrawler.DungeonCrawler;
import com.uroria.dungeoncrawler.util.FileBuilder;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.*;

public class StructureManager {

    /*
    start area
    boss structure
    teleport room to room instead 1 dungeon
     */

    private final World world;
    private final Map<Integer, Structure> structureMap;
    private final FileBuilder fileBuilder;

    public StructureManager() {
        if(Bukkit.getWorld("structureWorld") == null) {
            this.world = Bukkit.createWorld(new WorldCreator("structureWorld").type(WorldType.FLAT).generateStructures(false));
        } else this.world = Bukkit.getWorld("structureWorld");
        this.structureMap = new HashMap<>();
        this.fileBuilder = new FileBuilder("structure");
        loadStructureCache();
        showStructure();
    }

    public void teleportPlayer(Player player) {
        player.teleport(world.getSpawnLocation());
    }

    @Beta
    public List<Structure> getRandomStructures() {
        List<Structure> list = new ArrayList<>();
        structureMap.forEach((integer, structure) -> list.add(structure));
        return list;
    }

    public Structure getStructure(int id) {
        return structureMap.get(id);
    }

    private void showStructure() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(DungeonCrawler.instance, () -> structureMap.forEach((integer, structure) -> {
            structure.getStructureCube().showCube(Particle.VILLAGER_HAPPY);
            structure.getDoorCube().showCube(Particle.FLAME);
        }), 0, 20);
    }

    private void loadStructureCache() {
        fileBuilder.getStrings(false).forEach(s -> {
            int id = Integer.parseInt(fileBuilder.getString(s + ".id"));
            StructureType type = StructureType.valueOf(fileBuilder.getString(s + ".type"));
            Location l1 = fileBuilder.getLocation(s + ".l1");
            Location l2 = fileBuilder.getLocation(s + ".l2");
            Location door1 = fileBuilder.getLocation(s + ".door1");
            Location door2 = fileBuilder.getLocation(s + ".door2");
            structureMap.put(id, new Structure(id, type, l1, l2, door1, door2));
            System.out.print("structure " + id + " was loaded");
        });
    }

    public void setupStructure(Player player, int id, StructureType type, Location l1, Location l2, Location door1, Location door2) {
        if(!l1.getWorld().equals(this.world) || !l2.getWorld().equals(this.world)) {
            player.sendMessage("Structures can only be set in the " + this.world.getName() + " world");
            return;
        }
        String path = "structure"+id;
        fileBuilder.setString(path + ".id", String.valueOf(id));
        fileBuilder.setString(path + ".type", type.name());
        fileBuilder.setLocation(path + ".l1", l1);
        fileBuilder.setLocation(path + ".l2", l2);
        fileBuilder.setLocation(path + ".door1", door1);
        fileBuilder.setLocation(path + ".door2", door2);
        player.sendMessage("structure: " + id + " was created");
    }

    public Map<Integer, Structure> getStructureMap() {
        return structureMap;
    }
}
