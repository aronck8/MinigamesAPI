package com.aronck.minigamesapi.elements.map;

import com.aronck.minigamesapi.elements.locations.LocationChooser;
import com.aronck.minigamesapi.elements.teams.Team;
import com.aronck.minigamesapi.elements.teams.TeamsConfiguration;
import com.aronck.minigamesapi.utils.PluginUtils;
import org.bukkit.World;

public class GameMapBuilder {

    private GameMap gameMap;

    public GameMapBuilder(String name){
        gameMap = new GameMap(name);
    }

    public GameMapBuilder(String name, World world){
        gameMap = new GameMap(name, world);
    }

    public GameMapBuilderStageTwo setTeamsConfiguration(TeamsConfiguration teamsConfiguration) {
        gameMap.teamsConfiguration = teamsConfiguration;
        return new GameMapBuilderStageTwo(gameMap);
    }

}
