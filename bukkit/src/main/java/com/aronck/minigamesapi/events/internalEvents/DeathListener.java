package com.aronck.minigamesapi.events.internalEvents;

import com.aronck.minigamesapi.elements.teams.Team;
import com.aronck.minigamesapi.minigame.Minigame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public final class DeathListener implements Listener {

    private Minigame minigame;

    public DeathListener(Minigame minigame) {
        this.minigame = minigame;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Team team = minigame.getTeamOfPlayer(event.getEntity());
        if(team!=null)team.handlePlayerDeathEvent(event);
    }
}
