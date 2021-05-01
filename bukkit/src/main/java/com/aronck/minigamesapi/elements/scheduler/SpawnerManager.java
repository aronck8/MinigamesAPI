package com.aronck.minigamesapi.elements.scheduler;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class SpawnerManager {

    private ArrayList<Spawner> spawners = new ArrayList<>();

    private JavaPlugin main;

    public SpawnerManager(JavaPlugin main) {
        this.main = main;
    }

    public void startSpawners(){
        spawners.forEach(spawner -> spawner.start(main));
    }

    public void stopSpawners(){
        spawners.forEach(Spawner::stop);
    }

    public ArrayList<Spawner> getSpawners(){
        return spawners;
    }

    public Spawner get(int index) {
        return spawners.get(index);
    }

    public boolean add(Spawner spawner) {
        return spawners.add(spawner);
    }

    public void clear() {
        spawners.clear();
    }
}
