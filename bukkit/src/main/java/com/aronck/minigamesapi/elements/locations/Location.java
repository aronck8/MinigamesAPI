package com.aronck.minigamesapi.elements.locations;

import org.bukkit.Material;

public class Location {

	private String key;
	private org.bukkit.Location location;

	public Location(String key, org.bukkit.Location location) {
		this.key = key;
		this.location = location;
		Locations.cache.put(key, this);
	}

	public boolean isBlockPresent(Material material){
		return location.getBlock().getType().equals(material);
	}

	public String getKey() {
		return key;
	}

	public org.bukkit.Location getBukkitLocation() {
		return location;
	}

}
