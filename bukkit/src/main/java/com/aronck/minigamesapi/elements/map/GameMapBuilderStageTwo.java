package com.aronck.minigamesapi.elements.map;

import com.aronck.minigamesapi.elements.locations.LocationChooser;
import com.aronck.minigamesapi.elements.spawner.Spawner;
import com.aronck.minigamesapi.elements.teams.Conditional;
import com.aronck.minigamesapi.elements.teams.Team;
import com.aronck.minigamesapi.elements.teams.TeamsConfiguration;
import com.aronck.minigamesapi.utils.PluginUtils;

public class GameMapBuilderStageTwo {

    private GameMap gameMap;

    GameMapBuilderStageTwo(GameMap gameMap){
        this.gameMap = gameMap;
    }

    public GameMapBuilderStageTwo setMapConfiguration(MapConfiguration configuration){
        gameMap.configuration = configuration;
        return this;
    }

    public GameMapBuilderStageTwo addLocationChooser(LocationChooser chooser, int... teams) {
        if (teams.length == 0) gameMap.locationChoosers.add(new LocationChooser(gameMap.getName() + "-" + chooser.getKey(),
                chooser.getType(),
                PluginUtils.getItem(chooser.getItem(), gameMap.getName() + "-" + chooser.getKey())));
        if (gameMap.teamsConfiguration == null) return this;

        if (teams.length == 1 && teams[0] == TeamsConfiguration.ALL_TEAMS) {

            for (Team team : gameMap.teamsConfiguration.getTeams()) {

                //rename a copy of the item to have the teams name as suffix. Example: bed-1, or bed-red;
                //and add it to the list of location choosers
                gameMap.locationChoosers.add(new LocationChooser(gameMap.getName() + "-" + chooser.getKey() + "-" + team.getData().getName(),
                        chooser.getType(),
                        PluginUtils.getItem(chooser.getItem(), gameMap.getName() + "-" +chooser.getKey() + "-" + team.getData().getName())));
            }

        } else {
            for (int teamId : teams) {

                if (teamId < 0 || teamId > gameMap.teamsConfiguration.getTeams().size()) continue;

                Team team = gameMap.teamsConfiguration.getTeams().get(teamId);
                if (team == null) return this;

                //rename a copy of the item to have the teams name as suffix. Example: bed-1, or bed red;
                //and add it to the list of location choosers
                gameMap.locationChoosers.add(new LocationChooser(gameMap.getName() + "-" + chooser.getKey() + "-" + team.getData().getName(),
                        chooser.getType(),
                        PluginUtils.getItem(chooser.getItem(), gameMap.getName() + "-" + chooser.getKey() + "-" + team.getData().getName())));
            }
        }
        return this;
    }

    public GameMapBuilderStageTwo addSpawner(Spawner<?> spawner) {
        gameMap.spawnerManager.add(spawner);
        return this;
    }

    public GameMapBuilderStageTwo addStartCondition(Conditional startCondition) {
        gameMap.startConditions.add(startCondition);
        return this;
    }

    public GameMap build(){
        return gameMap;
    }

}
