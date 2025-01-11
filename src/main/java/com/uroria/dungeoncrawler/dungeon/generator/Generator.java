package com.uroria.dungeoncrawler.dungeon.generator;

import com.google.common.annotations.Beta;
import com.uroria.dungeoncrawler.util.Cube;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.codehaus.plexus.util.FileUtils;

import java.util.*;

public class Generator {

    private final Map<Integer, World> dungeonWorldMap;
    private final StructureManager structureManager;

    public Generator() {
        this.dungeonWorldMap = new HashMap<>();
        this.structureManager = new StructureManager();
    }

    public void generate(int dungeonId) {
        World dungeonWorld = generateWorld(dungeonId);
        copyBlocks(structureManager.getStructure(3).getStructureCube().blockList(), dungeonWorld.getSpawnLocation());
        //generateAndConnectStructures(structureManager.getStructure(1), structureManager.getStructure(2), dungeonWorld);
        /*
                structureManager.getRandomStructures().forEach(structure -> structure.getStructureCube().blockList().forEach(block -> {
            Block copyBlock = dungeonWorld.getBlockAt(block.getLocation());
            copyBlock.setType(block.getType());
            copyBlock.setBlockData(block.getBlockData());
            dungeonWorld.refreshChunk(copyBlock.getX(), copyBlock.getZ());
        }));
         */
    }

    @Beta
    public void teleportPlayer(Player player, int dungeonId) {
        player.teleport(dungeonWorldMap.get(dungeonId).getSpawnLocation());
    }

    private World generateWorld(int id) {
        World world = Bukkit.createWorld(new WorldCreator("dungeon-"+id).type(WorldType.FLAT).generateStructures(false));
        System.out.print("dungeonWorld with id " + id + " was created");
        dungeonWorldMap.put(id, world);
        return world;
    }

    public void deleteWorld(int id) {
        if(!dungeonWorldMap.containsKey(id)) {
            System.out.print("dungeonWorld with id " + id + " dos not exist");
            return;
        }
        World deletedWorld = dungeonWorldMap.get(id);
        deletedWorld.getEntities().forEach(entity -> {
            if(entity.getType().equals(EntityType.PLAYER)) {
                entity.teleport(Objects.requireNonNull(Bukkit.getWorld("world")).getSpawnLocation());
            }
        });

        Bukkit.getServer().unloadWorld(deletedWorld, false);

        try {
            if(deletedWorld.getWorldFolder().exists()) {
                FileUtils.deleteDirectory(deletedWorld.getWorldFolder());
            }
        } catch (Exception exception) {
            exception.fillInStackTrace();
        }
        deletedWorld.getWorldFolder().deleteOnExit();
        dungeonWorldMap.remove(id);
    }

    public void deleteAllWorld() {
        dungeonWorldMap.forEach((integer, world) -> deleteWorld(integer));
    }

    public StructureManager getStructureManager() {
        return structureManager;
    }

    public Map<Integer, World> getDungeonWorldMap() {
        return dungeonWorldMap;
    }

    public void generateAndConnectStructures(Structure structure1, Structure structure2, World dungeonWorld) {
        // Generiere die Blöcke für Structure 1
        generateStructureBlocks(structure1, dungeonWorld);

        // Verbinde Structure 1 mit Structure 2
        connectStructures(structure1, structure2);

        // Generiere die Blöcke für Structure 2
        generateStructureBlocks(structure2, dungeonWorld);
    }

    private void generateStructureBlocks(Structure structure, World dungeonWorld) {
        List<Block> structureBlocks = structure.getStructureCube().blockList();

        for (Block block : structureBlocks) {
            // Setze den Block in der Zielwelt
            // Beachte: Du müsstest hier auch die Daten des Blocks kopieren (Material, Metadaten usw.)
            Location targetLocation = getTargetLocation(structure, block.getLocation(), dungeonWorld);
            targetLocation.getBlock().setType(block.getType());
        }
    }

    private Location getTargetLocation(Structure structure, Location originalLocation, World dungeonWorld) {
        // Berechne die Zielposition für den Block in der Zielwelt
        int xOffset = originalLocation.getBlockX() - structure.getL1().getBlockX();
        int yOffset = originalLocation.getBlockY() - structure.getL1().getBlockY();
        int zOffset = originalLocation.getBlockZ() - structure.getL1().getBlockZ();

        Location targetLocation = structure.getL2().clone().add(xOffset, yOffset, zOffset);
        targetLocation.setWorld(dungeonWorld);

        return targetLocation;
    }

    private void connectStructures(Structure structure1, Structure structure2) {
        // Verbinde die Türen der beiden Strukturen
        connectDoors(structure1.getDoorCube(), structure2.getDoorCube());
    }

    private void connectDoors(Cube door1, Cube door2) {
        // Verbinde die Türen, indem du die Blöcke zwischen ihnen kopierst
        World world1 = door1.getCenter().getWorld();
        World world2 = door2.getCenter().getWorld();

        List<Block> door1Blocks = door1.blockList();
        List<Block> door2Blocks = door2.blockList();

        // Kopiere die Blöcke von door1 zu door2
        for (int i = 0; i < Math.min(door1Blocks.size(), door2Blocks.size()); i++) {
            Block door1Block = door1Blocks.get(i);
            Block door2Block = door2Blocks.get(i);

            // Kopiere die Daten des Blocks von door1 zu door2
            door2Block.setType(door1Block.getType());
            door2Block.setBlockData(door1Block.getBlockData()); // Daten kopieren (abhängig von der Minecraft-Version)
            // Kopiere alle weiteren Attribute oder Daten, die du übernehmen möchtest

            // Setze den Block in Welt-Y auf denselben Typ und dieselben Daten wie in Welt-X
            Block correspondingBlock = world2.getBlockAt(door2Block.getX(), door2Block.getY(), door2Block.getZ());
            correspondingBlock.setType(door2Block.getType());
            correspondingBlock.setBlockData(door2Block.getBlockData()); // Daten kopieren (abhängig von der Minecraft-Version)
            // Kopiere alle weiteren Attribute oder Daten, die du übernehmen möchtest
        }
    }

    public void copyBlocks(List<Block> sourceBlocks, Location targetLocation) {
        World targetWorld = targetLocation.getWorld();

        for (Block sourceBlock : sourceBlocks) {
            // Berechne die relativen Koordinaten des Blocks in Bezug auf die Start-Location
            int relativeX = sourceBlock.getX() - sourceBlocks.get(0).getX();
            int relativeY = sourceBlock.getY() - sourceBlocks.get(0).getY();
            int relativeZ = sourceBlock.getZ() - sourceBlocks.get(0).getZ();

            // Berechne die Ziel-Koordinaten basierend auf der relativen Position
            int targetX = targetLocation.getBlockX() + relativeX;
            int targetY = targetLocation.getBlockY() + relativeY;
            int targetZ = targetLocation.getBlockZ() + relativeZ;

            // Hole den Ziel-Block
            Block targetBlock = targetWorld.getBlockAt(targetX, targetY, targetZ);

            // Kopiere die Daten des Quell-Blocks zum Ziel-Block
            targetBlock.setType(sourceBlock.getType());
            targetBlock.setBlockData(sourceBlock.getBlockData()); // Daten kopieren (abhängig von der Minecraft-Version)
            // Kopiere alle weiteren Attribute oder Daten, die du übernehmen möchtest
        }
    }
}
