package com.aronck.minigamesapi.events.custom;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.PlayerDeathEvent;

public class FinalDeathEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    private PlayerDeathEvent event;

    public FinalDeathEvent(PlayerDeathEvent event) {
        this.event = event;
    }

    public PlayerDeathEvent getPlayerDeathEvent() {
        return event;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList(){
        return handlerList;
    }
}
