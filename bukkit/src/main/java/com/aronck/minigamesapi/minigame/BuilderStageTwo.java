package com.aronck.minigamesapi.minigame;

import com.aronck.minigamesapi.elements.event.Event;
import com.aronck.minigamesapi.elements.event.IEvent;
import com.aronck.minigamesapi.elements.locations.LocationChooser;
import com.aronck.minigamesapi.elements.map.MapConfiguration;
import com.aronck.minigamesapi.elements.scheduler.Spawner;
import com.aronck.minigamesapi.elements.teams.Conditional;
import com.aronck.minigamesapi.elements.teams.Team;
import com.aronck.minigamesapi.elements.teams.TeamsConfiguration;
import com.aronck.minigamesapi.utils.PluginUtils;
import org.bukkit.inventory.ItemStack;

public class BuilderStageTwo extends MinigameBuilderStage {

    BuilderStageTwo(Minigame minigame) {
        super(minigame);
    }

    public BuilderStageTwo addEvent(Event event) {
        minigame.eventsManager.addEvent(event.getClazz(), event.getAction());
        return this;
    }

    public <T extends org.bukkit.event.Event> BuilderStageTwo addEvent(Class<T> clazz, IEvent<T> event){
        minigame.eventsManager.addEvent(clazz, event);
        return this;
    }

    public BuilderStageTwo setMapConfiguration(MapConfiguration mapConfiguration) {
        minigame.mapConfiguration = mapConfiguration;
        mapConfiguration.setTeamsConfiguration(minigame.teamsConfiguration);
        return this;
    }

    public BuilderStageTwo addSpawner(Spawner spawner) {
        minigame.spawnerManager.add(spawner);
        return this;
    }

    public BuilderStageTwo setLocationChooserItem(ItemStack item) {
        minigame.locationChooserItem = item;
        return this;
    }

    public BuilderStageTwo setTeamsChooserItem(ItemStack item) {
        minigame.locationChooserItem = item;
        return this;
    }

    public BuilderStageTwo addStartCondition(Conditional startCondition) {
        minigame.startConditions.add(startCondition);
        return this;
    }

    public BuilderStageTwo addLocationChooser(LocationChooser chooser, int... teams) {
        if (teams.length == 0) minigame.locationChoosers.add(chooser);
        if (minigame.teamsConfiguration == null) return this;

        if (teams.length == 1 && teams[0] == TeamsConfiguration.ALL_TEAMS) {

            for (Team team : minigame.teamsConfiguration.getTeams()) {

                //rename a copy of the item to have the teams name as suffix. Example: bed 1, or bed-red;
                //and add it to the list of location choosers
                minigame.locationChoosers.add(new LocationChooser(chooser.getKey() + "-" + team.getName(),
                        chooser.getType(),
                        PluginUtils.getItem(chooser.getItem(), chooser.getKey() + "-" + team.getName())));
            }

        } else {
            for (int teamId : teams) {

                if (teamId < 0 || teamId > minigame.teamsConfiguration.getTeams().size()) continue;

                Team team = minigame.teamsConfiguration.getTeams().get(teamId);
                if (team == null) return this;

                //rename a copy of the item to have the teams name as suffix. Example: bed 1, or bed red;
                //and add it to the list of location choosers
                minigame.locationChoosers.add(new LocationChooser(chooser.getKey() + "-" + team.getName(),
                        chooser.getType(),
                        PluginUtils.getItem(chooser.getItem(), chooser.getKey() + "-" + team.getName())));
            }
        }
        return this;
    }

    public BuilderStageTwo setCountdown(CountDown countdown) {
        minigame.countDown = countdown;
        return this;
    }

    public Minigame build() {
        minigame.registerListeners();
        minigame.startPreStartTask();
        return minigame;
    }

}
