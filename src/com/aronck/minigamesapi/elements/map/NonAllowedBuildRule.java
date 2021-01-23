package com.aronck.minigamesapi.elements.map;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class NonAllowedBuildRule extends BuildRule{

    public NonAllowedBuildRule() {
        super(BuildRuleType.NEGATIVE);
    }

    @Override
    public boolean isAllowed(Player player, Block block) {
        return false;
    }
}
