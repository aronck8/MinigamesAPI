package com.aronck.minigamesapi.elements.map;

import com.aronck.minigamesapi.elements.teams.TeamsConfiguration;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public abstract class BuildRule {

    protected Map map;

    protected TeamsConfiguration teamsConfiguration;

    protected BuildRuleType type;

    public BuildRule(BuildRuleType type) {
        this.type = type;
    }

    final void setMap(Map map){
        this.map = map;
    }

    final void setTeamsConfiguration(TeamsConfiguration teamsConfiguration){
        this.teamsConfiguration = teamsConfiguration;
    }

    public BuildRuleType getType() {
        return type;
    }

    public abstract boolean isAllowed(Player player, Block block);
}
