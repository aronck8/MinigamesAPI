package com.aronck.minigamesapi.utils;

import com.aronck.minigamesapi.elements.locations.Location;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public final class Config {

	private static File configFile = new File("plugins/minigamesAPI/data.yml");
	private static FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);


	public static void saveLocation(Location location){
		config.set("locations." + location.getKey() + ".world", location.getBukkitLocation().getWorld().getName());
		config.set("locations." + location.getKey() + ".x", location.getBukkitLocation().getBlockX());
		config.set("locations." + location.getKey() + ".y", location.getBukkitLocation().getBlockY());
		config.set("locations." + location.getKey() + ".z", location.getBukkitLocation().getBlockZ());
		save();
	}

	public static Location getLocation(String key){
		String world = config.getString("locations." + key + ".world");
		if(world==null)return null;
		int x = config.getInt("locations." + key + ".x");
		int y = config.getInt("locations." + key + ".y");
		int z = config.getInt("locations." + key + ".z");
		return new Location(key, new org.bukkit.Location(Bukkit.getWorld(world), x, y, z));
	}

	private static void save() {
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
