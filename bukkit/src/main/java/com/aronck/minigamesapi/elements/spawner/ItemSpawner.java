package com.aronck.minigamesapi.elements.spawner;

import com.aronck.minigamesapi.utils.PluginUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class ItemSpawner extends Spawner{

    private ArrayList<ItemStack> items = new ArrayList<>();
    private int[] spawnChances;
    private int maxItemsLayingOnSpawner;

    public ItemSpawner(Location location, long delay, long duration, Material material){
        super(location, delay, duration);
        items.add(new ItemStack(material));
    }

    public ItemSpawner(Location location, long delay, long duration, ItemStack item) {
        super(location, delay, duration);
        items.add(item);
    }

    public ItemSpawner(Location location, long delay, long duration, int[] spawnChances, Material... materials){
        super(location, delay, duration);
        items.addAll(Arrays.stream(materials).map(ItemStack::new).collect(Collectors.toList()));
        this.spawnChances = spawnChances;
    }

    public ItemSpawner(Location location, long delay, long duration, int[] spawnChances, ItemStack... itemStacks){
        super(location, delay, duration);
        items.addAll(Arrays.asList(itemStacks));
        this.spawnChances = spawnChances;
    }

    public ItemSpawner(ArrayList<Location> locations, long delay, long duration, Material material){
        super(locations, delay, duration);
        items.add(new ItemStack(material));
    }

    public ItemSpawner(ArrayList<Location> locations, long delay, long duration, ItemStack item) {
        super(locations, delay, duration);
        items.add(item);
    }

    public ItemSpawner(ArrayList<Location> locations, long delay, long duration, int[] spawnChances, Material... materials){
        super(locations, delay, duration);
        items.addAll(Arrays.stream(materials).map(ItemStack::new).collect(Collectors.toList()));
        this.spawnChances = spawnChances;
    }

    public ItemSpawner(ArrayList<Location> locations, long delay, long duration, int[] spawnChances, ItemStack... itemStacks){
        super(locations, delay, duration);
        items.addAll(Arrays.asList(itemStacks));
        this.spawnChances = spawnChances;
    }

    public boolean canNewObjectSpawn(Location location){
        Collection<Entity> nearbyEntites = location.getWorld().getNearbyEntities(location, 2, 2, 2);
        int total = 0;
        for(Entity entity : nearbyEntites){
            if(entity instanceof Item){
                Item item = (Item) entity;
                total += item.getItemStack().getAmount();
            }
        }
        return total< maxItemsLayingOnSpawner;
    }

    @Override
    public void spawn() {
        for(Location location : locations) {
            if(location==null)continue;
            if(!canNewObjectSpawn(location))return;
            for (int i = 0; i < items.size(); i++) {
                //check if chances array has defined a value for this item
                if (spawnChances != null && spawnChances.length > i) {
                    //checks probability
                    int randomInt = PluginUtils.getRandomInt(100);
                    if (randomInt < spawnChances[i]) {
                        location.getWorld().dropItemNaturally(location, items.get(i));
                    }
                } else {
                    location.getWorld().dropItemNaturally(location, items.get(i));
                }
            }
        }
    }

}
