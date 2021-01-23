package com.aronck.minigamesapi.events.internalEvents;

import com.aronck.minigamesapi.elements.teams.Team;
import com.aronck.minigamesapi.minigame.Minigame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnEvent implements Listener {

    private Minigame minigame;

    public RespawnEvent(Minigame minigame) {
        this.minigame = minigame;
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        Team team = minigame.getTeamOfPlayer(event.getPlayer());
        if(team!=null)team.handlePlayerRespawnEvent(event);
    }
}
