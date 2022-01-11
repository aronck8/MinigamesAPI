package com.aronck.minigamesapi.elements.spawner;

import org.bukkit.inventory.ItemStack;

public class ItemSpawnerBuilder extends SpawnerBuilder<ItemSpawner, ItemStack>{

    public ItemSpawnerBuilder(long delay, long duration) {
        super(delay, duration);
    }

    @Override
    protected ItemSpawner getNewSpawner() {
        return new ItemSpawner();
    }
}
