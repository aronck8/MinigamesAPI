package com.aronck.minigamesapi.elements.locations;

import com.aronck.minigamesapi.utils.Config;

import java.util.ArrayList;
import java.util.HashMap;

public final class Locations {

	private Locations(){}

	static HashMap<String, Location> cache = new HashMap<>();

	public static void saveLocation(String key, org.bukkit.Location location){
		cache.put(key, new Location(key, location));
		Config.saveLocation(cache.get(key));
	}

	public static Location getLocation(String key){
		if(cache.get(key)!=null)return cache.get(key);
		Location location = Config.getLocation(key);
		if(location!=null) cache.put(key, location);
		return location;
	}

	public static ArrayList<Location> getLocations(String... keys){
		ArrayList<Location> locations = new ArrayList<>();
		for(String key : keys)locations.add(getLocation(key));
		return locations;
	}

	public static org.bukkit.Location getBukkitLocation(String key){
		Location loc = getLocation(key);
		if(loc==null)return null;
		return loc.getBukkitLocation();
	}

	public static ArrayList<org.bukkit.Location> getBukkitLocations(String... keys){
		ArrayList<org.bukkit.Location> locations = new ArrayList<>();
		for(String key : keys)locations.add(getBukkitLocation(key));
		return locations;
	}

	public static boolean isSet(String key){
		if(cache.get(key)!=null)return true;
		cache.put(key, Config.getLocation(key));
		return cache.get(key)!=null;
	}

	public static boolean areSet(String... keys){
		for(String key : keys){
			if(!isSet(key))return false;
		}
		return true;
	}

}
