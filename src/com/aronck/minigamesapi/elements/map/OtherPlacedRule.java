package com.aronck.minigamesapi.elements.map;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class OtherPlacedRule extends BuildRule{

    public OtherPlacedRule(BuildRuleType type) {
        super(type);
    }

    @Override
    public boolean isAllowed(Player player, Block block) {
        if(map==null)return false;
        if(map.placedBlocks==null)return false;
        if(map.placedBlocks.get(player)==null)return false;

        return !(map.placedBy.get(block)!=null && map.placedBy.get(block).equals(player));
    }
}
