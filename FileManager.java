package org.david.minecraftv2.FileSaver;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class FileManager {

    // First: File
    // Second: Configuration (config) != config.yml | will be locations.yml

    public static File file = new File("player_data.yml");
    public static FileConfiguration config = YamlConfiguration.loadConfiguration(file);

    public void setLocation(Player player, Location location, Colors color) {
        config.set(player.getUniqueId().toString() + "." + color.toString() + ".X", location.getX());
        config.set(player.getUniqueId().toString() + "." + color.toString() + ".Y", location.getY());
        config.set(player.getUniqueId().toString() + "." + color.toString() + ".Z", location.getZ());
        config.set(player.getUniqueId().toString() + "." + color.toString() + ".world", location.getWorld().getName().toString());
        config.set(player.getUniqueId().toString() + "." + color.toString() + ".yaw", location.getYaw());
        config.set(player.getUniqueId().toString() + "." + color.toString() + ".pitch", location.getPitch());

        save();
    }

    public Location getLocation(Player player, Colors color) {

        if(config.get(player.getUniqueId().toString() + "." + color.toString() + ".X") == null) return null;
        double x = config.getDouble(player.getUniqueId().toString() + "." + color.toString() + ".X");
        double y = config.getDouble(player.getUniqueId().toString() + "." + color.toString() + ".Y");
        double z = config.getDouble(player.getUniqueId().toString() + "." + color.toString() + ".Z");
        String world = config.getString(player.getUniqueId().toString() + "." + color.toString() + ".world");

        float yaw = (float) config.getDouble(player.getUniqueId().toString() + "." + color.toString() + ".yaw");
        float pitch = (float) config.getDouble(player.getUniqueId().toString() + "." + color.toString() + ".pitch");

        Location location = new Location(Bukkit.getWorld(world), x, y, z);
        location.setPitch(pitch);
        location.setYaw(yaw);

        return location;
    }

    public void removeLocation(Player player, Colors color) {
        config.set(player.getUniqueId().toString() + "." + color.toString(), null);

        save();
    }

    public void save() {
        try{
            config.save(file);
        } catch (Exception e) {
            System.out.println("Saving the location player data was unsuccessfull!");
            e.printStackTrace();
        }
    }

}

