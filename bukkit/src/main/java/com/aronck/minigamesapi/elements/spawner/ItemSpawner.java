package com.aronck.minigamesapi.elements.spawner;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class ItemSpawner extends Spawner<ItemStack>{

    @Override
    protected int getSpawnedObjectsCurrentlyOnSpawnerAmount(Location location) {
        Collection<Entity> nearbyEntites = location.getWorld().getNearbyEntities(location, 2, 2, 2);
        int total = 0;
        for(Entity entity : nearbyEntites){
            if(entity instanceof Item){
                Item item = (Item) entity;
                total += item.getItemStack().getAmount();
            }
        }
        return total;
    }

    @Override
    public void spawnElement(Location location, ItemStack itemStack) {
        location.getWorld().dropItemNaturally(location, itemStack);
    }
}