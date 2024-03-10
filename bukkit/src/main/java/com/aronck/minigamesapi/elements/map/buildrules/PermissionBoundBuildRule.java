package com.aronck.minigamesapi.elements.map.buildrules;

import com.aronck.minigamesapi.elements.map.GameMap;
import com.aronck.minigamesapi.elements.teams.TeamsConfiguration;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class PermissionBoundBuildRule extends BuildRule{

    private String permission;

    public PermissionBoundBuildRule(BuildRuleType ruleType, BuildActionType actionType, String permission) {
        super(ruleType, actionType);
        this.permission = permission;
    }

    @Override
    public boolean isAllowed(GameMap gameMap, TeamsConfiguration teamsConfiguration, Player player, Block block) {
        return player.hasPermission(permission);
    }
}
