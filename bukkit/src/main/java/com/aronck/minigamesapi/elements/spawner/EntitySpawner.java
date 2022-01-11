package com.aronck.minigamesapi.elements.spawner;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class EntitySpawner extends Spawner<EntityType>{

    @Override
    public void spawnElement(Location location, EntityType type) {
        location.getWorld().spawnEntity(location, type);
    }

    @Override
    protected int getSpawnedObjectsCurrentlyOnSpawnerAmount(Location location) {
        return 0;
    }

}
