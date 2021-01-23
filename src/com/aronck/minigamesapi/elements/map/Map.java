package com.aronck.minigamesapi.elements.map;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Map {

    HashMap<Player, List<Block>> placedBlocks = new HashMap<>();
    HashMap<Block, Player> placedBy = new HashMap<>();

    private World world;

    public Map(World world) {
        this.world = world;
    }

    public void processBlockBreakEvent(BlockBreakEvent event){
        addBrokenBlock(event.getBlock(), event.getPlayer());
    }

    public void processBlockPlacedEvent(BlockPlaceEvent event){
        addPlacedBlock(event.getBlock(), event.getPlayer());
    }

    public void addPlacedBlock(Block block, Player player){
        placedBlocks.computeIfAbsent(player, k -> new ArrayList<>());
        placedBlocks.get(player).add(block);
        placedBy.put(block, player);
    }

    public void addBrokenBlock(Block block, Player player){
        for(List<Block> blockList : placedBlocks.values()){
            for (int i = blockList.size()-1; i >=0; i--) {
                Block block1 = blockList.get(i);
                if (block1.getLocation().equals(block.getLocation())){
                    blockList.remove(i);
                }
            }
        }
    }

}
