package com.aronck.minigamesapi.events.internalEvents;

import com.aronck.minigamesapi.minigame.Minigame;
import com.aronck.minigamesapi.elements.teams.Team;
import com.aronck.minigamesapi.elements.teams.TeamChooserType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public final class JoinEvent implements Listener {

    private Minigame minigame;

    public JoinEvent(Minigame minigame) {
        this.minigame = minigame;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(minigame.getTeamsConfiguration()==null)return;
        if(minigame.getTeamsConfiguration().getType().equals(TeamChooserType.AUTOMATIC)) {
            ArrayList<Team> teams = minigame.getTeamsConfiguration().getTeams();
            if(teams.isEmpty())return;
            Team maxPeopleLeftTeam = teams.get(0);
            for(int i = 0; i < teams.size(); i++) {
                Team team = teams.get(i);
                if (maxPeopleLeftTeam.getNumberOfFreeSlots() < team.getNumberOfFreeSlots()) {
                    maxPeopleLeftTeam = teams.get(i);
                }
            }
            System.out.println(e.getPlayer().getDisplayName() + " has joined the team: " + maxPeopleLeftTeam.getName());
            minigame.getTeamsConfiguration().addPlayerToTeam(e.getPlayer(), maxPeopleLeftTeam);
        }

    }
}
