package com.aronck.minigamesapi.events.custom;

import com.aronck.minigamesapi.minigame.Minigame;
import com.aronck.minigamesapi.elements.teams.Team;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameOverEvent extends Event implements Cancellable {

    private static HandlerList handlerList = new HandlerList();

    private Minigame minigame;
    private Team winningTeam;

    private boolean cancelled = false;

    public GameOverEvent(Minigame minigame, Team winningTeam) {
        this.minigame = minigame;
        this.winningTeam = winningTeam;
    }

    public Minigame getMinigame(){
        return minigame;
    }

    public Team getWinningTeam() {
        return winningTeam;
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
