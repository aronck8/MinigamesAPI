package com.aronck.minigamesapi.elements.map.buildrules;

import com.aronck.minigamesapi.elements.map.GameMap;
import com.aronck.minigamesapi.elements.teams.TeamsConfiguration;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class AllAllowedBuildRule extends BuildRule{

    public AllAllowedBuildRule(BuildActionType actionType) {
        super(BuildRuleType.POSITIVE, actionType);
    }

    @Override
    public boolean isAllowed(GameMap gameMap, TeamsConfiguration teamsConfiguration, Player player, Block block) {
        return true;
    }
}
