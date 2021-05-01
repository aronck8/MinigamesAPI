package com.aronck.minigamesapi.elements.map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class BlockTypeBuildRule extends BuildRule{

    private List<Material> allowedMaterials;

    public BlockTypeBuildRule(BuildRuleType type, Material... allowedMaterials) {
        super(type);
        this.allowedMaterials = Arrays.asList(allowedMaterials);
    }

    @Override
    public boolean isAllowed(Player player, Block block) {
        return allowedMaterials.contains(block.getType());
    }
}