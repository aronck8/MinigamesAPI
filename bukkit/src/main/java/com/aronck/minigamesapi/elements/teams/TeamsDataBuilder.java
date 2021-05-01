package com.aronck.minigamesapi.elements.teams;

import com.aronck.minigamesapi.elements.teams.kit.Kit;
import org.bukkit.Location;

public class TeamsDataBuilder {

	private TeamsData action;

	public TeamsDataBuilder(){
		action = new TeamsData();
	}

	public TeamsDataBuilder addRespawnCondition(Conditional condition){
		action.respawnConditions.add(condition);
		return this;
	}

	public TeamsDataBuilder addRespawnLocation(Location location){
		action.respawnLocations.add(location);
		return this;
	}

	public TeamsDataBuilder addKit(Kit kit){
		action.kits.add(kit);
		return this;
	}

	public TeamsDataBuilder addWinCondition(Conditional conditional){
		action.winConditions.add(conditional);
		return this;
	}

	public TeamsData build(){
		return action;
	}

}
