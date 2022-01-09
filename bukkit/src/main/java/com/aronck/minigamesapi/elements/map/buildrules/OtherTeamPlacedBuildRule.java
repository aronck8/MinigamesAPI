package com.aronck.minigamesapi.elements.map.buildrules;

import com.aronck.minigamesapi.elements.map.GameMap;
import com.aronck.minigamesapi.elements.teams.Team;
import com.aronck.minigamesapi.elements.teams.TeamsConfiguration;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class OtherTeamPlacedBuildRule extends BuildRule{

    public OtherTeamPlacedBuildRule(BuildRuleType type, BuildActionType actionType) {
        super(type, actionType);
    }

    @Override
    public boolean isAllowed(GameMap gameMap, TeamsConfiguration teamsConfiguration, Player player, Block block) {
        if(teamsConfiguration==null)return true;
        Team team = teamsConfiguration.getTeamOfPlayer(player);
        if(team==null)return true;
        return !team.equals(teamsConfiguration.getTeamOfPlayer(gameMap.getPlacedBy().get(block)));
    }
}
