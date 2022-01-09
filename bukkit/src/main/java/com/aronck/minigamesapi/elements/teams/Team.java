package com.aronck.minigamesapi.elements.teams;

import com.aronck.minigamesapi.events.custom.FinalDeathEvent;
import com.aronck.minigamesapi.minigame.Minigame;
import com.aronck.minigamesapi.utils.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Team {

    //main properties of a team
    private final int ID;
    private int maxPlayers;

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
        data = new TeamsDataBuilder(ID + "").build();
    }

    void initTeam(Minigame minigame){
    }

    public ItemStack getTeamItem(){
        return data.getTeamItem(this);
    }

    public int getID(){
        return ID;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getNumberOfUsedSlots(){
        return players.size();
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

    public boolean hasFreeSlots(){
        return getNumberOfFreeSlots()>0;
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
