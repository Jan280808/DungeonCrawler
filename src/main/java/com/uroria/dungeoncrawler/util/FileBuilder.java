package com.uroria.dungeoncrawler.util;

import com.uroria.dungeoncrawler.DungeonCrawler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileBuilder {

    /**
     *  The YamlFile create configs
     */

    private final File file;
    private final YamlConfiguration yamlConfiguration;

    public FileBuilder(String fileName) {
        this.file = new File("plugins/" + DungeonCrawler.instance.getName() + "/" + fileName + ".yml");
        this.yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        if(!file.exists()) setString("README", "debug...");
    }

    /**
     *  save a file
     */
    public void saveFile() {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  puts a string in file
     */

    public void setString(String path, String value) {
        yamlConfiguration.set(path, value);
        saveFile();
    }

    /**
     *  get a stringList from file
     */

    public List<String> getStrings(boolean key) {
        return new ArrayList<>(yamlConfiguration.getKeys(key));
    }

    public String getString(String path) {
        return yamlConfiguration.getString(path);
    }

    /**
     *  set a location in file
     */

    public void setLocation(String path, Location location) {
        yamlConfiguration.set(path + ".X", location.getX());
        yamlConfiguration.set(path + ".Y", location.getY());
        yamlConfiguration.set(path + ".Z", location.getZ());
        yamlConfiguration.set(path + ".Yaw", location.getYaw());
        yamlConfiguration.set(path + ".Pitch", location.getPitch());
        yamlConfiguration.set(path + ".World", location.getWorld().getName());
        saveFile();
    }

    /**
     *  get a location from file
     */

    public Location getLocation(String path) {
        try {
            String world = yamlConfiguration.getString(path + ".World");
            double x = yamlConfiguration.getDouble(path + ".X");
            double y = yamlConfiguration.getDouble(path + ".Y");
            double z = yamlConfiguration.getDouble(path + ".Z");
            double yaw = yamlConfiguration.getDouble(path + ".Yaw");
            double pitch = yamlConfiguration.getDouble(path + ".Pitch");
            Location location = new Location(Bukkit.getWorld(Objects.requireNonNull(world)), x, y, z);
            location.setYaw((float)yaw);
            location.setPitch((float)pitch);
            return location;
        } catch (Exception exception) {
            return null;
        }
    }

    public boolean getBoolean(String path) {
        return yamlConfiguration.getBoolean(path);
    }
}
