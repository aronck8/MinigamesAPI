package com.aronck.minigamesapi.elements.teams.kit;

import com.aronck.minigamesapi.utils.PluginUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class RandomizedInventory extends Kit{

    private HashMap<ItemStack, Integer> probabilityOfItems = new HashMap<>();

    public RandomizedInventory(ItemStack... items){
        Stream.of(items).forEach((item) -> probabilityOfItems.put(item, 100));
    }

    public RandomizedInventory(int[] probabilities, ItemStack... items){
        for (int i = 0; i < items.length; i++) {
            if (probabilities.length > i){
                probabilityOfItems.put(items[i], probabilities[i]);
            }else{
                probabilityOfItems.put(items[i], 100);
            }
        }
    }

    public Kit generateKit(){
        return new Kit(generateItemArray());
    }

    public ItemStack[] generateItemArray(){
        ItemStack[] items = new ItemStack[probabilityOfItems.size()];
        int currentIndex = 0;
        for(ItemStack item : probabilityOfItems.keySet()){
            if(PluginUtils.getRandomInt(100)<probabilityOfItems.get(item))items[currentIndex++] = item;
        }

        //cut out the empty space
        return Arrays.copyOfRange(items, 0, currentIndex);
    }

    public ItemStack[] generateItemListWithSpaces(int availableSpace){
        ItemStack[] items = new ItemStack[availableSpace];
        int currentIndex = 0;
        for(ItemStack item : probabilityOfItems.keySet()){
            if(currentIndex>=availableSpace)return items;
            if(PluginUtils.getRandomInt(100)<probabilityOfItems.get(item))items[currentIndex++] = item;
        }
        List<ItemStack> itemList = new ArrayList<>(Arrays.asList(Arrays.copyOfRange(items, 0, currentIndex)));
        while (itemList.size()<availableSpace){
            itemList.add(PluginUtils.getRandomInt(itemList.size()-1), new ItemStack(Material.AIR));
        }

        //reuse the old items array
        return itemList.toArray(items);
    }

}
