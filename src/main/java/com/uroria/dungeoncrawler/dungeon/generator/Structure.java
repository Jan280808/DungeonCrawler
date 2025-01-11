package com.uroria.dungeoncrawler.dungeon.generator;

import com.uroria.dungeoncrawler.util.Cube;
import org.bukkit.Location;

public class Structure {

    private final int id;
    private final StructureType type;
    private final Location l1, l2;
    private final Cube structureCube, doorCube;
    private final Location door1, door2;

    public Structure(int id, StructureType type, Location l1, Location l2, Location door1, Location door2) {
        this.id = id;
        this.type = type;
        this.l1 = l1;
        this.l2 = l2;
        this.structureCube = new Cube(l1, l2);
        this.door1 = door1;
        this.door2 = door2;
        this.doorCube = new Cube(door1, door2);
    }

    public int getId() {
        return id;
    }

    public StructureType getType() {
        return type;
    }

    public Location getL1() {
        return l1;
    }

    public Location getL2() {
        return l2;
    }

    public Cube getStructureCube() {
        return structureCube;
    }

    public Location getDoor1() {
        return door1;
    }

    public Location getDoor2() {
        return door2;
    }

    public Cube getDoorCube() {
        return doorCube;
    }
}
