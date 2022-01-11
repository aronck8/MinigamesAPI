package com.aronck.minigamesapi.elements.spawner;

import org.bukkit.entity.EntityType;

public class EntitySpawnerBuilder extends SpawnerBuilder<EntitySpawner, EntityType> {

    public EntitySpawnerBuilder(long delay, long duration) {
        super(delay, duration);
    }

    @Override
    protected EntitySpawner getNewSpawner() {
        return new EntitySpawner();
    }
}
