package com.aronck.minigamesapi.elements.spawner;

import com.aronck.minigamesapi.utils.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import tests.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Spawner<T> {

    protected List<Location> locations = new ArrayList<>();
    protected HashMap<T, Integer> spawnableElements = new HashMap<>();
    protected int maxOnSpawnerLayingItems = -1;
    protected int currentSpawnCycles = 0;
    protected int maxSpawnCycles = -1;
    protected long delay;
    protected long duration;
    protected int taskId;

    public void setLocation(int i, Location location) {
        this.locations.set(i, location);
    }

    public void start(){
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), this::spawn, delay, duration);
    }

    protected void spawn(){
        for(Location location : locations) {
            if (location == null) continue;
            if (!canNewObjectSpawn(location)) return;
            for(Map.Entry<T, Integer> entry : spawnableElements.entrySet()){
                int randomInt = PluginUtils.getRandomInt(100);
                if(randomInt < entry.getValue()){
                    spawnElement(location, entry.getKey());
                }
            }
            currentSpawnCycles++;
        }
    }

    protected abstract void spawnElement(Location location, T element);

    protected boolean canNewObjectSpawn(Location location){
        if(currentSpawnCycles>=maxSpawnCycles && maxSpawnCycles!=-1)return false;
        if(getSpawnedObjectsCurrentlyOnSpawnerAmount(location)>=maxOnSpawnerLayingItems && maxOnSpawnerLayingItems!=-1)return false;
        return true;
    }

    protected abstract int getSpawnedObjectsCurrentlyOnSpawnerAmount(Location location);

    public void stop(){
        if(taskId!=0) Bukkit.getScheduler().cancelTask(taskId);
    }

}
