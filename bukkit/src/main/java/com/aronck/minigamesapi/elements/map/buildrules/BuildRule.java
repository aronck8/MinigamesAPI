package com.aronck.minigamesapi.elements.map.buildrules;

import com.aronck.minigamesapi.elements.map.GameMap;
import com.aronck.minigamesapi.elements.teams.TeamsConfiguration;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public abstract class BuildRule {

    protected GameMap gameMap;

    protected TeamsConfiguration teamsConfiguration;

    protected BuildRuleType ruleType;
    protected BuildActionType actionType;

    public BuildRule(BuildRuleType ruleType, BuildActionType actionType) {
        this.ruleType = ruleType;
        this.actionType = actionType;
    }

    public BuildRuleType getRuleType() {
        return ruleType;
    }

    public BuildActionType getActionType() {
        return actionType;
    }

    public abstract boolean isAllowed(GameMap gameMap, TeamsConfiguration teamsConfiguration, Player player, Block block);
}
