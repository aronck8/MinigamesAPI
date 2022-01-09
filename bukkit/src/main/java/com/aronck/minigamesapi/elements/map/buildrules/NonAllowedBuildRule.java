package com.aronck.minigamesapi.elements.map.buildrules;

import com.aronck.minigamesapi.elements.map.GameMap;
import com.aronck.minigamesapi.elements.teams.TeamsConfiguration;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class NonAllowedBuildRule extends BuildRule{

    public NonAllowedBuildRule(BuildActionType actionType) {
        super(BuildRuleType.NEGATIVE, actionType);
    }

    @Override
    public boolean isAllowed(GameMap gameMap, TeamsConfiguration teamsConfiguration, Player player, Block block) {
        return false;
    }
}
