package com.aronck.minigamesapi.events.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ShopBuyEvent<K, V> extends Event implements Cancellable {

    private static HandlerList handlerList = new HandlerList();

    private boolean cancelled = false;

    private Player player;

    private K object;

    private V value;

    public ShopBuyEvent(Player player, K object, V value) {
        this.player = player;
        this.object = object;
        this.value = value;
    }

    public Player getPlayer() {
        return player;
    }

    public K getObject() {
        return object;
    }

    public V getValue() {
        return value;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList(){
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
