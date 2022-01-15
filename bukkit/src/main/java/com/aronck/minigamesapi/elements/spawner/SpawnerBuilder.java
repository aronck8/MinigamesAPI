package com.aronck.minigamesapi.elements.spawner;

import org.bukkit.Location;

import java.util.Arrays;

public abstract class SpawnerBuilder<S extends Spawner<T>, T> {

    protected S spawner;

    public SpawnerBuilder(long delay, long duration){
        spawner = getNewSpawner();
        spawner.delay = delay;
        spawner.duration = duration;
    }

    public SpawnerBuilder<S, T> addLocation(Location location){
        spawner.locations.add(location);
        return this;
    }

    public SpawnerBuilder<S, T> addLoactions(Location... locations){
        spawner.locations.addAll(Arrays.asList(locations));
        return this;
    }

    public SpawnerBuilder<S, T> addSpawnableType(T t){
        spawner.spawnableElements.put(t, 100);
        return this;
    }

    public SpawnerBuilder<S, T> addSpawnableTypes(T... tArray){
        for(T t : tArray)addSpawnableType(t);
        return this;
    }

    public SpawnerBuilder<S, T> addSpawnableType(T t, int probability){
        spawner.spawnableElements.put(t, probability);
        return this;
    }

    public SpawnerBuilder<S, T> addMaxOnSpawnerLayingItems(int maxItems){
        spawner.maxOnSpawnerLayingItems = maxItems;
        return this;
    }

    public SpawnerBuilder<S, T> setMaxSpawnCycles(int maxSpawnCycles){
        spawner.maxSpawnCycles = maxSpawnCycles;
        return this;
    }

    public S build(){
        return spawner;
    }

    protected abstract S getNewSpawner();

}
