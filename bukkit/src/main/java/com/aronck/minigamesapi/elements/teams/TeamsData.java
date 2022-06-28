package com.aronck.minigamesapi.elements.teams;

import com.aronck.minigamesapi.elements.teams.kit.Kit;
import com.aronck.minigamesapi.minigame.Minigame;
import com.aronck.minigamesapi.utils.PluginUtils;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class TeamsData {

	String name;
	DyeColor teamColor;

	List<Conditional> respawnConditions;
	List<Location> respawnLocations;
	List<Kit> kits;
	List<Function<Team, Boolean>> winConditions;
	BiConsumer<Minigame, Team> initMethod;
	Function<Team, ItemStack> createItemStack = team -> PluginUtils.getItem(Material.BED, 1,
			"Team: " + team.getData().getName(),
			team.getNumberOfUsedSlots() + "/" + team.getMaxPlayers());
	boolean canJoinTeamAfterStart = false;

	TeamsData(){
		respawnConditions = new ArrayList<>();
		respawnLocations = new ArrayList<>();
		kits = new ArrayList<>();
		winConditions = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public DyeColor getTeamColor() {
		return teamColor;
	}

	public ItemStack getTeamItem(Team team) {
		return createItemStack.apply(team);
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

	public List<Function<Team, Boolean>> getWinConditions() {
		return winConditions;
	}

	public boolean canJoinTeamAfterStart() {
		return canJoinTeamAfterStart;
	}
}
