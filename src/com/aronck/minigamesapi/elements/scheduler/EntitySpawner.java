package com.aronck.minigamesapi.elements.scheduler;

import com.aronck.minigamesapi.utils.PluginUtils;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Arrays;

public class EntitySpawner extends Spawner{

    private ArrayList<EntityType> entities = new ArrayList<>();
    private int[] spawnChances;

    public EntitySpawner(Location location, long delay, long duration, EntityType entity) {
        super(location, delay, duration);
        entities.add(entity);
    }

    public EntitySpawner(Location location, long delay, long duration, int[] spawnChances, EntityType... entities){
        super(location, delay, duration);
        this.entities.addAll(Arrays.asList(entities));
        this.spawnChances = spawnChances;
    }

    public EntitySpawner(ArrayList<Location> locations, long delay, long duration, EntityType entity) {
        super(locations, delay, duration);
        entities.add(entity);
    }

    public EntitySpawner(ArrayList<Location> locations, long delay, long duration, int[] spawnChances, EntityType... entities){
        super(locations, delay, duration);
        this.entities.addAll(Arrays.asList(entities));
        this.spawnChances = spawnChances;
    }

    @Override
    public void spawn() {
        for(Location location : locations) {
            for (int i = 0; i < entities.size(); i++) {
                //check if chances array has defined a value for this item
                if (spawnChances != null && spawnChances.length > i) {
                    //checks probability
                    int randomInt = PluginUtils.getRandomInt(100);
                    if (randomInt < spawnChances[i]) {
                        location.getWorld().spawnEntity(location, entities.get(i));
                    }
                } else {
                    location.getWorld().spawnEntity(location, entities.get(i));
                }
            }
        }
    }

}
