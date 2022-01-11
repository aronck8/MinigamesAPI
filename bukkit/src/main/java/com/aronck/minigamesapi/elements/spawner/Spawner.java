package com.aronck.minigamesapi.elements.spawner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import tests.Main;

import java.util.ArrayList;
import java.util.List;

public abstract class Spawner {

    protected List<Location> locations = new ArrayList<>();
    protected long delay;
    protected long duration;
    protected int taskId;

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

    public void setLocation(int i, Location location) {
        this.locations.set(i, location);
    }

    public void start(){
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), this::spawn, delay, duration);
    }

    protected abstract void spawn();

    protected abstract boolean canNewObjectSpawn(Location location);

    public void stop(){
        if(taskId!=0) Bukkit.getScheduler().cancelTask(taskId);
    }

}
