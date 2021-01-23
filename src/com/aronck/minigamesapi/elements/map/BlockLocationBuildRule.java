package com.aronck.minigamesapi.elements.map;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class BlockLocationBuildRule extends BuildRule{

    private List<Location> allowedLocations;

    public BlockLocationBuildRule(BuildRuleType type, Location... locations){
        super(type);
        allowedLocations = Arrays.asList(locations);
    }

    @Override
    public boolean isAllowed(Player player, Block block) {
        return allowedLocations.contains(block.getLocation());
    }
}
