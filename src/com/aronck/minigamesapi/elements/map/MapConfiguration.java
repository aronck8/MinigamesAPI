package com.aronck.minigamesapi.elements.map;

import com.aronck.minigamesapi.elements.teams.TeamsConfiguration;
import com.aronck.minigamesapi.elements.teams.kit.Kit;
import com.aronck.minigamesapi.utils.PluginUtils;
import com.aronck.minigamesapi.utils.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MapConfiguration {

    private Map map;

    //Teamsconfiguration used for the BuildRules e.g. a buildrule that checks
    //if the block has been placed by a players teammate
    private TeamsConfiguration teamsConfiguration;

    private List<BuildRule> buildRules = new ArrayList<>();

    private HashMap<List<Location>, List<Tuple<Integer, List<ItemStack>>>> chestContents = new HashMap<>();

    public MapConfiguration(){
        this(new Map(Bukkit.getWorld("world")));
    }

    public MapConfiguration(Map map) {
        this.map = map;
    }

    public void handleBlockBreak(BlockBreakEvent event){

        for(BuildRule buildRule : buildRules){
            if(buildRule.getType().equals(BuildRuleType.POSITIVE)){
                //if one positive BuildRule is true, allow the action
                if(buildRule.isAllowed(event.getPlayer(), event.getBlock())){
                    break;
                }
            }else if(buildRule.getType().equals(BuildRuleType.NEGATIVE)){
                if(!buildRule.isAllowed(event.getPlayer(), event.getBlock())){
                    event.setCancelled(true);
                    break;
                }
            }
        }

        if(!event.isCancelled())map.processBlockBreakEvent(event);

    }

    public void handleBlockPlaced(BlockPlaceEvent event){
        map.processBlockPlacedEvent(event);
    }

    public MapConfiguration addBuildRule(BuildRule buildRule){
        buildRule.setMap(map);
        buildRules.add(buildRule);
        return this;
    }

    public void setTeamsConfiguration(TeamsConfiguration teamsConfiguration) {
        this.teamsConfiguration = teamsConfiguration;
        for(BuildRule buildRule : buildRules){
            buildRule.setTeamsConfiguration(teamsConfiguration);
        }
    }

    public MapConfiguration addKitChest(Location location, ItemStack... content){
        addKitChest(location, Arrays.asList(content), 100);
        return this;
    }

    public MapConfiguration addKitChest(Location location, List<ItemStack> content, int probability){
        addKitChests(Collections.singletonList(location), content, probability);
        return this;
    }



    public MapConfiguration addKitChests(List<Location> locations, ItemStack... content){
        addKitChests(locations, Arrays.asList(content), 100);
        return this;
    }

    public MapConfiguration addKitChests(List<Location> locations, List<ItemStack> content, int probability){
        chestContents.computeIfAbsent(locations, k -> new ArrayList<>());
        chestContents.get(locations).add(new Tuple<>(probability, content));
        return this;
    }

    List<Tuple<Integer, List<ItemStack>>> getListOfKitsAndProbabilities(Location location){
        for (List<Location> locations : chestContents.keySet()) {
            if (locations.contains(location)) return chestContents.get(locations);
        }
        return null;
    }

    List<Tuple<Integer, List<ItemStack>>> getListOfKitsAndProbabilities(List<Location> locations){
        for (List<Location> locations1 : chestContents.keySet()) {
            if (locations1.containsAll(locations)) return chestContents.get(locations1);
        }
        return null;
    }

    void fillChest(Location location){
        Block block = location.getBlock();
        if(!(block.getType().equals(Material.CHEST))) block.setType(Material.CHEST);

        Chest chest = (Chest) block.getState();
        chest.getBlockInventory().setContents(getKit(location));
    }

    ItemStack[] getKit(Location location){

        List<Tuple<Integer, List<ItemStack>>> listOfProbabilitiesAndKits = getListOfKitsAndProbabilities(location);

        listOfProbabilitiesAndKits.sort(Comparator.comparing(Tuple::getK));

        for(Tuple<Integer, List<ItemStack>> tuple : listOfProbabilitiesAndKits){
            if(PluginUtils.getRandomInt(100)<=tuple.getK())return tuple.getV().toArray(new ItemStack[0]);
        }
        return Kit.EMPTY_KIT.getContents();
    }

    public void fillChests(){
        chestContents.keySet().forEach(locations -> locations.stream().filter(Objects::nonNull).forEach(this::fillChest));
    }

    /**
     *
     * good luck
     * @return
     */
    public HashMap<List<Location>, List<Tuple<Integer, List<ItemStack>>>> getChestContents() {
        return chestContents;
    }

    public void resetMap() {

    }
}
