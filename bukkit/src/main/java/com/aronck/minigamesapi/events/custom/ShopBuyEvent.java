package com.aronck.minigamesapi.events.custom;

import com.aronck.minigamesapi.elements.shop.ShopElement;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ShopBuyEvent<K, V> extends Event implements Cancellable {

    private static HandlerList handlerList = new HandlerList();

    private boolean cancelled = false;

    private Player player;

    private ShopElement<K, V> shopElement;

    public ShopBuyEvent(Player player, ShopElement<K, V> shopElement) {
        this.player = player;
        this.shopElement = shopElement;
    }

    public Player getPlayer() {
        return player;
    }

    public ShopElement<K, V> getShopElement() {
        return shopElement;
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
