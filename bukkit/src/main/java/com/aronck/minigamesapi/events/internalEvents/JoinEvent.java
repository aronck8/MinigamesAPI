package com.aronck.minigamesapi.events.internalEvents;

import com.aronck.minigamesapi.minigame.Minigame;
import com.aronck.minigamesapi.elements.teams.TeamChooserType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class JoinEvent implements Listener {

    private Minigame minigame;

    public JoinEvent(Minigame minigame) {
        this.minigame = minigame;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(minigame.getTeamsConfiguration()==null)return;
        if(minigame.getTeamsConfiguration().getType().equals(TeamChooserType.AUTOMATIC)) {
            minigame.getTeamsConfiguration().positionPlayerInTeam(e.getPlayer(), minigame.isIngamePhase());
        }

    }
}
