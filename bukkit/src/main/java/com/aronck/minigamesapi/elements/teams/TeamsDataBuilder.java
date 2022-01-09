package com.aronck.minigamesapi.elements.teams;

import com.aronck.minigamesapi.elements.teams.kit.Kit;
import com.aronck.minigamesapi.minigame.Minigame;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class TeamsDataBuilder {

	private TeamsData teamsData;

	public TeamsDataBuilder(String name){
		teamsData = new TeamsData();
		teamsData.name = name;
	}

	public TeamsDataBuilder addColor(DyeColor color){
		teamsData.teamColor = color;
		return this;
	}

	public TeamsDataBuilder addTeamsItem(Function<Team, ItemStack> itemStackProvider){
		teamsData.createItemStack = itemStackProvider;
		return this;
	}

	public TeamsDataBuilder addRespawnCondition(Conditional condition){
		teamsData.respawnConditions.add(condition);
		return this;
	}

	public TeamsDataBuilder addRespawnLocation(Location location){
		teamsData.respawnLocations.add(location);
		return this;
	}

	public TeamsDataBuilder addKit(Kit kit){
		teamsData.kits.add(kit);
		return this;
	}

	public TeamsDataBuilder addWinCondition(Conditional conditional){
		teamsData.winConditions.add(conditional);
		return this;
	}

	public TeamsDataBuilder withInitMethod(BiConsumer<Minigame, Team> initMethod){
		teamsData.initMethod = initMethod;
		return this;
	}

	public TeamsDataBuilder enableJoinTeamAfterGameStart(){
		teamsData.canJoinTeamAfterStart = true;
		return this;
	}

	public TeamsDataBuilder disableJoinTeamAfterGameStart(){
		teamsData.canJoinTeamAfterStart = false;
		return this;
	}

	public TeamsData build(){
		return teamsData;
	}

}
