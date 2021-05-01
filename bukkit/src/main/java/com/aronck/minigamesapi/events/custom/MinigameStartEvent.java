package com.aronck.minigamesapi.events.custom;

import com.aronck.minigamesapi.minigame.Minigame;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MinigameStartEvent extends Event implements Cancellable {

    private static HandlerList handlerList = new HandlerList();

    private Minigame minigame;

    private boolean cancelled = false;

    public MinigameStartEvent(Minigame minigame) {
        this.minigame = minigame;
    }

    public Minigame getMinigame(){
        return minigame;
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
