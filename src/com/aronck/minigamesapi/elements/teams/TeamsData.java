package com.aronck.minigamesapi.elements.teams;

import com.aronck.minigamesapi.elements.teams.kit.Kit;
import com.aronck.minigamesapi.utils.PluginUtils;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class TeamsData {

	List<Conditional> respawnConditions;
	List<Location> respawnLocations;
	List<Kit> kits;
	List<Conditional> winConditions;

	TeamsData(){
		respawnConditions = new ArrayList<>();
		respawnLocations = new ArrayList<>();
		kits = new ArrayList<>();
		winConditions = new ArrayList<>();
	}

	public List<Conditional> getRespawnConditions() {
		return respawnConditions;
	}


	public List<Location> getRespawnLocations() {
		return respawnLocations;
	}

	public Location getRandomRespawnLocationFromList(){
		if(respawnLocations.isEmpty())return null;
		return respawnLocations.get(PluginUtils.getRandomInt(respawnLocations.size()-1));
	}

	public List<Kit> getKits() {
		return kits;
	}

	public Kit getRandomKitFromList(){
		if(kits.isEmpty()) return Kit.EMPTY_KIT;
		return kits.get(PluginUtils.getRandomInt(kits.size()-1));
	}

	public List<Conditional> getWinConditions() {
		return winConditions;
	}

}
