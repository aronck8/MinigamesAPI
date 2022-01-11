package com.aronck.minigamesapi.elements.map;

import com.aronck.minigamesapi.elements.locations.LocationChooser;
import com.aronck.minigamesapi.elements.spawner.SpawnerManager;
import com.aronck.minigamesapi.elements.teams.Conditional;
import com.aronck.minigamesapi.elements.teams.TeamsConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameMap {

    private final String name;

    MapConfiguration configuration = new MapConfiguration();
    TeamsConfiguration teamsConfiguration;
    SpawnerManager spawnerManager = new SpawnerManager();

    private HashMap<Player, List<Block>> placedBlocks = new HashMap<>();
    private HashMap<Block, Player> placedBy = new HashMap<>();
    List<LocationChooser> locationChoosers = new ArrayList<>();
    List<Conditional> startConditions = new ArrayList<>();

    private World world;

    GameMap(String name){
        this(name, Bukkit.getWorld(name));
    }

    GameMap(String name, World world) {
        this.name = name;
        this.world = world;
    }

    public void processBlockBreakEvent(BlockBreakEvent event){
        configuration.handleBlockBreak(this, teamsConfiguration, event);
        if(!event.isCancelled()) addBrokenBlock(event.getBlock(), event.getPlayer());
    }

    public void processBlockPlacedEvent(BlockPlaceEvent event){
        configuration.handleBlockPlaced(this, teamsConfiguration, event);
        if(!event.isCancelled()) addPlacedBlock(event.getBlock(), event.getPlayer());
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

    public void resetMap() {

    }

    public String getName() {
        return name;
    }

    public TeamsConfiguration getTeamsConfiguration() {
        return teamsConfiguration;
    }

    public MapConfiguration getConfiguration() {
        return configuration;
    }

    public HashMap<Player, List<Block>> getPlacedBlocks() {
        return placedBlocks;
    }

    public HashMap<Block, Player> getPlacedBy() {
        return placedBy;
    }

    public SpawnerManager getSpawnerManager() {
        return spawnerManager;
    }

    public List<LocationChooser> getLocationChoosers() {
        return locationChoosers;
    }

    public List<Conditional> getStartConditions() {
        return startConditions;
    }

    public World getWorld() {
        return world;
    }

    public void initOnMinigameStart() {
        teamsConfiguration.initializeTeamsOnMinigameStart();
        configuration.fillChests();
        getSpawnerManager().startSpawners();
    }

    public void performOnGameStopTasks(){
        getSpawnerManager().stopSpawners();
    }
}
