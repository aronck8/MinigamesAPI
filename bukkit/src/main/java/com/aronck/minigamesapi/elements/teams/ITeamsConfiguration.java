package com.aronck.minigamesapi.elements.teams;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public interface ITeamsConfiguration {

    void initializeTeamsOnMinigameStart();

    TeamsConfiguration setTeamsData(TeamsData data, int... teamNumbers);

    ArrayList<TeamsData> getTeamsData();

    ArrayList<Team> getTeams();

    TeamChooserType getType();

    boolean addPlayerToTeam(Player player, Team team, boolean hasGameStarted);

    Team getTeamOfPlayer(Player player);


    /**
     *
     * places a player in a random team to evenly distribute the players.
     *
     * @param player the player we want to put in a team
     * @return true if the player could be placed in any team i.e. if the teams aren't full yet
     */
    boolean positionPlayerInTeam(Player player, boolean hasGameStarted);
}
