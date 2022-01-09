package com.aronck.minigamesapi.elements.map.buildrules;

import com.aronck.minigamesapi.elements.map.GameMap;
import com.aronck.minigamesapi.elements.teams.TeamsConfiguration;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class OtherPlacedRule extends BuildRule{

    public OtherPlacedRule(BuildRuleType type, BuildActionType actionType) {
        super(type, actionType);
    }

    @Override
    public boolean isAllowed(GameMap gameMap, TeamsConfiguration teamsConfiguration, Player player, Block block) {
        if(gameMap ==null)return false;
        if(gameMap.getPlacedBlocks().get(player)==null)return false;

        return !player.equals(gameMap.getPlacedBy().get(block));
    }
}
