package com.aronck.minigamesapi.elements.map;

import com.aronck.minigamesapi.elements.teams.Team;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class OtherTeamPlacedBuildRule extends BuildRule{

    public OtherTeamPlacedBuildRule(BuildRuleType type) {
        super(type);
    }

    @Override
    public boolean isAllowed(Player player, Block block) {
        if(teamsConfiguration==null)return true;
        Team team = teamsConfiguration.getTeamOfPlayer(player);
        if(team==null)return true;
        return !team.equals(teamsConfiguration.getTeamOfPlayer(map.placedBy.get(block)));
    }
}
