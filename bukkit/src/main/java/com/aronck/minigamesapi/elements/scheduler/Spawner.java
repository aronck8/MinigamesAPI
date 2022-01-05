package com.aronck.minigamesapi.elements.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Spawner {

    protected List<Location> locations = new ArrayList<>();
    protected long delay;
    protected long duration;
    protected int taskId;
    protected int maxItemsLazingOnSpawner = 5;

    public Spawner(Location location, long delay, long duration){
        locations.add(location);
        this.delay = delay;
        this.duration = duration;
    }

    public Spawner(ArrayList<Location> locations, long delay, long duration){
        this.locations = locations;
        this.delay = delay;
        this.duration = duration;
    }

    public Spawner(Location location, long delay, long duration, int maxItemsLazingOnSpawner){
        locations.add(location);
        this.delay = delay;
        this.duration = duration;
        this.maxItemsLazingOnSpawner = maxItemsLazingOnSpawner;
    }

    public Spawner(ArrayList<Location> locations, long delay, long duration, int maxItemsLazingOnSpawner){
        this.locations = locations;
        this.delay = delay;
        this.duration = duration;
        this.maxItemsLazingOnSpawner = maxItemsLazingOnSpawner;
    }

    public void setLocation(int i, Location location) {
        this.locations.set(i, location);
    }

    public void start(JavaPlugin main){
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, this::spawn, delay, duration);
    }

    protected abstract void spawn();

    protected boolean canNewItemSpawn(Location location){
        Collection<Entity> nearbyEntites = location.getWorld().getNearbyEntities(location, 2, 2, 2);
        int total = 0;
        for(Entity entity : nearbyEntites){
            if(entity instanceof Item){
                Item item = (Item) entity;
                total += item.getItemStack().getAmount();
            }
        }
        return total<maxItemsLazingOnSpawner;
    }

    public void stop(){
        if(taskId!=0) Bukkit.getScheduler().cancelTask(taskId);
    }

}
