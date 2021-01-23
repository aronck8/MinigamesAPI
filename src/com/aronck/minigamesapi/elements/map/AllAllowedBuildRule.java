package com.aronck.minigamesapi.elements.map;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class AllAllowedBuildRule extends BuildRule{

    public AllAllowedBuildRule() {
        super(BuildRuleType.POSITIVE);
    }

    @Override
    public boolean isAllowed(Player player, Block block) {
        return true;
    }
}
