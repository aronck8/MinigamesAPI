package com.aronck.minigamesapi.elements.map;

import com.aronck.minigamesapi.Main;
import com.aronck.minigamesapi.elements.map.buildrules.BuildActionType;
import com.aronck.minigamesapi.elements.map.buildrules.BuildRule;
import com.aronck.minigamesapi.elements.map.buildrules.BuildRuleType;
import com.aronck.minigamesapi.elements.teams.TeamsConfiguration;
import com.aronck.minigamesapi.elements.teams.kit.Kit;
import com.aronck.minigamesapi.feedback.Feedback;
import com.aronck.minigamesapi.feedback.FeedbackType;
import com.aronck.minigamesapi.utils.FeedbackUtils;
import com.aronck.minigamesapi.utils.PluginUtils;
import com.aronck.minigamesapi.utils.Tuple;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MapConfiguration {

    private List<BuildRule> buildRules = new ArrayList<>();

    private HashMap<List<Location>, List<Tuple<Integer, List<ItemStack>>>> chestContents = new HashMap<>();

    public void handleBlockBreak(GameMap gameMap, TeamsConfiguration teamsConfiguration, BlockBreakEvent event){

        for(BuildRule buildRule : buildRules){
            if (BuildActionType.BREAK.equals(buildRule.getActionType()) || BuildActionType.BOTH.equals(buildRule.getActionType())) {
                if (buildRule.getRuleType().equals(BuildRuleType.POSITIVE)) {
                    //if one positive BuildRule is true, allow the action
                    if (buildRule.isAllowed(gameMap, teamsConfiguration, event.getPlayer(), event.getBlock())) {
                        break;
                    }
                } else if (buildRule.getRuleType().equals(BuildRuleType.NEGATIVE)) {
                    if (!buildRule.isAllowed(gameMap, teamsConfiguration, event.getPlayer(), event.getBlock())) {
                        event.setCancelled(true);
                        break;
                    }
                }
            }
        }

    }

    public void handleBlockPlaced(GameMap gameMap, TeamsConfiguration teamsConfiguration, BlockPlaceEvent event){
        for(BuildRule buildRule : buildRules){
            if(!(buildRule.getActionType().equals(BuildActionType.PLACE) || buildRule.getActionType().equals(BuildActionType.BOTH))) continue;
            if(buildRule.getRuleType().equals(BuildRuleType.POSITIVE)){
                //if one positive BuildRule is true, allow the action
                if(buildRule.isAllowed(gameMap, teamsConfiguration, event.getPlayer(), event.getBlock())){
                    break;
                }
            }else if(buildRule.getRuleType().equals(BuildRuleType.NEGATIVE)){
                if(!buildRule.isAllowed(gameMap, teamsConfiguration, event.getPlayer(), event.getBlock())){
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }

    public MapConfiguration addBuildRule(BuildRule buildRule){
        buildRules.add(buildRule);
        return this;
    }

    public MapConfiguration addKitChest(Location location, ItemStack... content){
        if(location==null){
            StackTraceElement source = FeedbackUtils.getFirstExternalStackTraceElement(new RuntimeException().getStackTrace());
            Main.getInstance().getFeedbackHandler().getFeedbackFetcher()
                    .add(new Feedback(FeedbackType.SOFT_WARNING, "The location for the kit chest is null! The chest won't be registered", source.getClassName() + ":" + source.getLineNumber()));
            return this;
        }
        addKitChest(location, Arrays.asList(content), 100);
        return this;
    }

    public MapConfiguration addKitChest(Location location, List<ItemStack> content, int probability){
        if(location==null){
            StackTraceElement source = FeedbackUtils.getFirstExternalStackTraceElement(new RuntimeException().getStackTrace());
            Main.getInstance().getFeedbackHandler().getFeedbackFetcher()
                    .add(new Feedback(FeedbackType.SOFT_WARNING, "The location for the kit chest is null! The chest won't be registered", source.getClassName() + ":" + source.getLineNumber()));
            return this;
        }
        addKitChests(Collections.singletonList(location), content, probability);
        return this;
    }

    public MapConfiguration addKitChests(List<Location> locations, ItemStack... content){
        if(locations.isEmpty()) {
            StackTraceElement source = FeedbackUtils.getFirstExternalStackTraceElement(new RuntimeException().getStackTrace());
            Main.getInstance().getFeedbackHandler().getFeedbackFetcher()
                    .add(new Feedback(FeedbackType.SOFT_WARNING, "The location for the kit chest is null! The chest won't be registered", source.getClassName() + ":" + source.getLineNumber()));
            return this;
        }
        addKitChests(locations, Arrays.asList(content), 100);
        return this;
    }

    public MapConfiguration addKitChests(List<Location> locations, List<ItemStack> content, int probability){
        if(locations.isEmpty()) {
            StackTraceElement source = FeedbackUtils.getFirstExternalStackTraceElement(new RuntimeException().getStackTrace());
            Main.getInstance().getFeedbackHandler().getFeedbackFetcher()
                    .add(new Feedback(FeedbackType.SOFT_WARNING, "The location for the kit chest is null! The chest won't be registered", source.getClassName() + ":" + source.getLineNumber()));
            return this;
        }
        chestContents.computeIfAbsent(locations, k -> new ArrayList<>());
        chestContents.get(locations).add(new Tuple<>(probability, content));
        return this;
    }

    private List<Tuple<Integer, List<ItemStack>>> getListOfKitsAndProbabilities(Location location){
        for (List<Location> locations : chestContents.keySet()) {
            if (locations.contains(location)) return chestContents.get(locations);
        }
        return new ArrayList<>();
    }

    private List<Tuple<Integer, List<ItemStack>>> getListOfKitsAndProbabilities(List<Location> locations){
        for (List<Location> locations1 : chestContents.keySet()) {
            if (locations1.containsAll(locations)) return chestContents.get(locations1);
        }
        return new ArrayList<>();
    }

    private void fillChest(Location location){
        Block block = location.getBlock();
        if(!(block.getType().equals(Material.CHEST))) block.setType(Material.CHEST);

        Chest chest = (Chest) block.getState();
        chest.getBlockInventory().setContents(getKit(location));
    }

    private ItemStack[] getKit(Location location){

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

}
