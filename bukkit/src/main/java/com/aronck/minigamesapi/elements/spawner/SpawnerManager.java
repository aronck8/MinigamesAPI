package com.aronck.minigamesapi.elements.spawner;

import java.util.ArrayList;

public class SpawnerManager {

    private ArrayList<Spawner<?>> spawners = new ArrayList<>();

    public void startSpawners(){
        spawners.forEach(Spawner::start);
    }

    public void stopSpawners(){
        spawners.forEach(Spawner::stop);
    }

    public ArrayList<Spawner<?>> getSpawners(){
        return spawners;
    }

    public Spawner<?> get(int index) {
        return spawners.get(index);
    }

    public boolean add(Spawner<?> spawner) {
        return spawners.add(spawner);
    }

    public void clear() {
        spawners.clear();
    }
}
