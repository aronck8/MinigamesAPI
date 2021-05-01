package com.aronck.minigamesapi.events.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FirstSpawnEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    private Player player;

    private int teamId;

    public FirstSpawnEvent(Player player, int teamId) {
        this.player = player;
        this.teamId = teamId;
    }

    public Player getPlayer() {
        return player;
    }

    public int getTeamId() {
        return teamId;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList(){
        return handlerList;
    }

}
