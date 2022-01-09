package com.aronck.minigamesapi.elements.map.buildrules;

import com.aronck.minigamesapi.elements.map.GameMap;
import com.aronck.minigamesapi.elements.teams.TeamsConfiguration;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class BlockLocationBuildRule extends BuildRule{

    private List<Location> allowedLocations;

    public BlockLocationBuildRule(BuildRuleType type, BuildActionType actionType, Location... locations){
        super(type, actionType);
        allowedLocations = Arrays.asList(locations);
    }

    @Override
    public boolean isAllowed(GameMap gameMap, TeamsConfiguration teamsConfiguration, Player player, Block block) {
        return allowedLocations.contains(block.getLocation());
    }
}
