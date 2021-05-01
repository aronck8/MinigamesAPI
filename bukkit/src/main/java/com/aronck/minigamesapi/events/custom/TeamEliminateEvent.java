package com.aronck.minigamesapi.events.custom;

import com.aronck.minigamesapi.elements.teams.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TeamEliminateEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    private Team team;
    private Player lastPlayer;

    public TeamEliminateEvent(Team team, Player lastPlayer){
        this.team = team;
        this.lastPlayer = lastPlayer;
    }

    public Team getTeam() {
        return team;
    }

    public Player getLastPlayer() {
        return lastPlayer;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList(){
        return handlerList;
    }

}
