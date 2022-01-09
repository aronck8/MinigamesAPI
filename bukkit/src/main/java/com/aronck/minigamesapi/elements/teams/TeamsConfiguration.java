package com.aronck.minigamesapi.elements.teams;

import com.aronck.minigamesapi.utils.PluginConstants;
import com.aronck.minigamesapi.utils.PluginUtils;
import org.apache.commons.lang.NullArgumentException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

public class TeamsConfiguration {

	public static final int ALL_TEAMS = -1;

	private ArrayList<Team> teams;
	private TeamChooserType type;

	private HashMap<UUID, Team> teamsOfPlayers = new HashMap<>();

	public TeamsConfiguration(String playerBalance, TeamChooserType type) {
		this.type = type;
		teams = new ArrayList<>();
		parsePlayerBalance(playerBalance);
	}

	public void initializeTeamsOnMinigameStart(){
		for (Team team : teams) {
			for (Player player : team.getPlayers()) {
				if(team.getData().getRandomRespawnLocationFromList()!=null)
					player.teleport(team.getData().getRandomRespawnLocationFromList());
				team.getData().getRandomKitFromList().applyToPlayer(player);
			}
		}
	}

	public TeamsConfiguration setTeamsData(TeamsData data, int... teamNumbers){
		if(teamNumbers.length==0) throw new NullArgumentException("There was no team specified to which the data should be set to.");
		for(int i : teamNumbers)
			if(i == ALL_TEAMS){
				teams.forEach(team -> team.data = data);
			}else{
				if(teams.get(i)==null)continue;
				teams.get(i).data = data;
			}
		return this;
	}

	public ArrayList<TeamsData> getTeamsData(){
		return (ArrayList<TeamsData>) teams.stream().map(Team::getData).collect(Collectors.toList());
	}

	public ArrayList<Team> getTeams() {
		return teams;
	}

	public TeamChooserType getType() {
		return type;
	}

	public boolean addPlayerToTeam(Player player, Team team, boolean hasGameStarted){
		if(hasGameStarted && !team.getData().canJoinTeamAfterStart()) return false;
		teamsOfPlayers.put(player.getUniqueId(), team);
		team.addPlayer(player);
		return true;
	}

	public Team getTeamOfPlayer(Player player){
		return teamsOfPlayers.get(player.getUniqueId());
	}

	private void parsePlayerBalance(String input){
		String[] args = input.toLowerCase().split("x");
		//format numberofTeams x playersperteam
		if(args.length==2){
			int numberOfTeams = Integer.parseInt(args[0]);
			int playersPerTeam = Integer.parseInt(args[1]);
			for (int i = 0; i < numberOfTeams; i++) {
				teams.add(new Team(i+1, playersPerTeam));
			}
		}else if(args.length==1){
			args = input.toLowerCase().split("v");
			//format 4v4v4v4
			for(int i = 0;i<args.length;i++){
				teams.add(new Team(i+1, Integer.parseInt(args[i])));
			}
		}
	}

	/**
	 *
	 * places a player in a random team to evenly distribute the players.
	 *
	 * @param player the player we want to put in a team
	 * @return true if the player could be placed in any team i.e. if the teams aren't full yet
	 */
    public boolean positionPlayerInTeam(Player player, boolean hasGameStarted) {
		if (teams.isEmpty()) return false;
		Team maxPeopleLeftTeam = teams.get(0);

		for (Team team : teams) {
			if(hasGameStarted&&!team.getData().canJoinTeamAfterStart())continue;
			//check which team has less players(in percentage)
			if (team.getNumberOfFreeSlots()/team.getMaxPlayers() > maxPeopleLeftTeam.getNumberOfFreeSlots()/team.getMaxPlayers()){
				maxPeopleLeftTeam = team;
			}
		}

		if(!maxPeopleLeftTeam.hasFreeSlots())return false;
		System.out.println(player.getDisplayName() + " has joined the team: " + maxPeopleLeftTeam.getData().getName());
		addPlayerToTeam(player, maxPeopleLeftTeam, hasGameStarted);
		return true;
    }

	/**
	 * Places a player in a team. If the specified team still has open slots, the player will be placed in that team,
	 * if not, he will be placed in any team calculated by the {@link #positionPlayerInTeam(Player, boolean)} method
	 *
	 * @param player the player we want to put in a team
	 * @param requestedTeam the team we'd like to put the player in.
	 * @return
	 */
	public boolean positionPlayerInTeamWithPreference(Player player, Team requestedTeam, boolean hasGameStarted) {
		if(requestedTeam.hasFreeSlots()){
			return addPlayerToTeam(player, requestedTeam, hasGameStarted);
		}
		positionPlayerInTeam(player, hasGameStarted);
		return false;
	}

	public Team getTeamFromItem(ItemStack item) {
		for(Team team : teams){
			if (item.equals(team.getData().getTeamItem())) return team;
		}
		return null;
	}

	public void openTeamsChoserInventory(Player player) {

		Inventory inventory = Bukkit.createInventory(player, PluginUtils.convertToMultiplesOfBase(teams.size(), 9), PluginConstants.TEAM_CHOOSER_INVENTORY_NAME);
		for(int i = 0; i<teams.size(); i++){
			inventory.setItem(i, teams.get(i).getData().getTeamItem());
		}

		player.openInventory(inventory);

	}
}
