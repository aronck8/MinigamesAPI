package com.aronck.minigamesapi.elements.map.buildrules;

import com.aronck.minigamesapi.elements.map.GameMap;
import com.aronck.minigamesapi.elements.teams.TeamsConfiguration;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class BlockTypeBuildRule extends BuildRule{

    private List<Material> allowedMaterials;

    public BlockTypeBuildRule(BuildRuleType type, BuildActionType actionType, Material... allowedMaterials) {
        super(type, actionType);
        this.allowedMaterials = Arrays.asList(allowedMaterials);
    }

    @Override
    public boolean isAllowed(GameMap gameMap, TeamsConfiguration teamsConfiguration, Player player, Block block) {
        return allowedMaterials.contains(block.getType());
    }
}