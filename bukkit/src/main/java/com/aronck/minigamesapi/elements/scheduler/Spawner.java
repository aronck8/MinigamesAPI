package com.aronck.minigamesapi.elements.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

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

    public void start(JavaPlugin main){
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, this::spawn, delay, duration);
    }

    protected abstract void spawn();

    public void stop(){
        if(taskId!=0) Bukkit.getScheduler().cancelTask(taskId);
    }

}
