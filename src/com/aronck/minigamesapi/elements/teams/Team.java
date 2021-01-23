package com.aronck.minigamesapi.elements.teams;

import com.aronck.minigamesapi.events.custom.FinalDeathEvent;
import com.aronck.minigamesapi.utils.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;
import java.util.List;

public class Team {

    //main properties of a team
    private final int ID;
    private int maxPlayers;
    private String name;
    private DyeColor teamColor;

    //ingame data like list of players, that are already dead
    private ArrayList<Player> players;
    private ArrayList<Player> deadPlayers;

    //gets set by the TeamsConfiguration and Minigame class
    TeamsData data;

    Team(int ID, int maxPlayers) {
        this.ID = ID;
        this.maxPlayers = maxPlayers;
        players = new ArrayList<>();
        deadPlayers = new ArrayList<>();
        data = new TeamsData();
        name = ID + "";
    }

    public int getID(){
        return ID;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public DyeColor getTeamColor() {
        return teamColor;
    }

    public void setTeamColor(DyeColor teamColor) {
        this.teamColor = teamColor;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public TeamsData getData() {
        return data;
    }

    public int getNumberOfFreeSlots(){
        return maxPlayers - players.size();
    }


    public void handlePlayerDeathEvent(PlayerDeathEvent event){
        if(!players.contains(event.getEntity()))return;

        if (!canRespawn()) {
            deadPlayers.add(event.getEntity());
            Bukkit.getPluginManager().callEvent(new FinalDeathEvent(event));
        }
    }

    public void handlePlayerRespawnEvent(PlayerRespawnEvent event){
        if(!players.contains(event.getPlayer()))return;

        if (canRespawn()) {
            event.setRespawnLocation(PluginUtils.getRandomObjectFromList(data.getRespawnLocations()));
        }
    }

    /**
     *
     * checks whether or not a player can respawn
     *
     * @return true if all of the {@link Conditional} respawn conditions evaluate to true
     */
    public boolean canRespawn(){
        for (Conditional c : data.getRespawnConditions()){
            if (!c.evaluate())return false;
        }
        return true;
    }

    /**
     *
     * Returns if all the players in the team have been eliminated
     *
     * @return true if all the players in the team have died, while the respawn conditions weren't true
     */
    public boolean isDead(){
        for(Player player : players){
            if(!deadPlayers.contains(player))return false;
        }
        return true;
    }

    /**
     *
     * Returns a list of all of the eliminated players
     *
     * @return the list of dead Players
     */
    public ArrayList<Player> getDeadPlayers() {
        return deadPlayers;
    }

    /**
     *
     * Allows a player to be resuscitated after his final death
     *
     * @param player    player to be resuscitated
     */
    public void resuscitatePlayer(Player player){
        deadPlayers.remove(player);
        List<Location> locations = getData().getRespawnLocations();
        if(locations.isEmpty())return;
        player.teleport(PluginUtils.getRandomObjectFromList(locations));
    }

}
