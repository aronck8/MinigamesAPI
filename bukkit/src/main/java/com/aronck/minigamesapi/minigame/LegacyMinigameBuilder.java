package com.aronck.minigamesapi.minigame;

import com.aronck.minigamesapi.elements.event.Event;
import com.aronck.minigamesapi.elements.locations.LocationChooser;
import com.aronck.minigamesapi.elements.map.MapConfiguration;
import com.aronck.minigamesapi.elements.scheduler.Spawner;
import com.aronck.minigamesapi.elements.teams.Conditional;
import com.aronck.minigamesapi.elements.teams.Team;
import com.aronck.minigamesapi.elements.teams.TeamsConfiguration;
import com.aronck.minigamesapi.utils.PluginUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

@Deprecated
public class LegacyMinigameBuilder {

	private Minigame minigame;

	public LegacyMinigameBuilder(JavaPlugin main){
		minigame = new Minigame(main);
	}

	public LegacyMinigameBuilder setTeams(TeamsConfiguration teams){
		minigame.teamsConfiguration = teams;
		return this;
	}

	@Deprecated
	public LegacyMinigameBuilder addEvent(Event<? extends org.bukkit.event.Event> event) {
		//minigame.eventsManager.addEvent(event.getClazz(), event.getAction());
		return this;
	}

	public LegacyMinigameBuilder addSpawner(Spawner spawner) {
		minigame.spawnerManager.add(spawner);
		return this;
	}

	public LegacyMinigameBuilder setMapConfiguration(MapConfiguration mapConfiguration) {
		minigame.mapConfiguration = mapConfiguration;
		return this;
	}

	public LegacyMinigameBuilder setLocationChooserItem(ItemStack item) {
		minigame.locationChooserItem = item;
		return this;
	}

	public LegacyMinigameBuilder setTeamsChooserItem(ItemStack item) {
		minigame.locationChooserItem = item;
		return this;
	}

	public LegacyMinigameBuilder addStartCondition(Conditional startCondition) {
		minigame.startConditions.add(startCondition);
		return this;
	}

	public LegacyMinigameBuilder addLocationChooser(LocationChooser chooser, int... teams) {
		if (teams.length == 0) minigame.locationChoosers.add(chooser);
		if (minigame.teamsConfiguration == null) return this;

		if (teams.length == 1 && teams[0] == TeamsConfiguration.ALL_TEAMS) {

			for (Team team : minigame.teamsConfiguration.getTeams()) {

				//rename a copy of the item to have the teams name as suffix. Example: bed 1, or bed red;
				//and add it to the list of location choosers
				minigame.locationChoosers.add(new LocationChooser(chooser.getKey() + " " + team.getName(),
						chooser.getType(),
						PluginUtils.getItem(chooser.getItem(), chooser.getKey() + " " + team.getName())));
			}

		} else {
			for (int teamId : teams) {

				if (teamId < 0 || teamId > minigame.teamsConfiguration.getTeams().size()) continue;

				Team team = minigame.teamsConfiguration.getTeams().get(teamId);
				if (team == null) return this;

				//rename a copy of the item to have the teams name as suffix. Example: bed 1, or bed red;
				//and add it to the list of location choosers
				minigame.locationChoosers.add(new LocationChooser(chooser.getKey() + " " + team.getName(),
						chooser.getType(),
						PluginUtils.getItem(chooser.getItem(), chooser.getKey() + " " + team.getName())));
			}
		}
		return this;
	}

	public LegacyMinigameBuilder setCountdown(Countdown countdown) {
		minigame.countDown = countdown;
		return this;
	}

	public Minigame build() {
		minigame.getCurrentState().start();
		if (minigame.mapConfiguration != null) minigame.mapConfiguration.fillChests();
		minigame.startPreStartTask();
		return minigame;
	}

}



