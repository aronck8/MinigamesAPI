package com.aronck.minigamesapi.elements.teams;

import org.bukkit.DyeColor;
import org.bukkit.entity.Player;

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

	public TeamsConfiguration(String playerBalance, TeamChooserType type, String... teamNames) {
		this.type = type;
		teams = new ArrayList<>();
		parsePlayerBalance(playerBalance);
		for (int i = 0; i < teamNames.length; i++) {
			String teamName = teamNames[i];
			teams.get(i).setName(teamName);
		}
	}

	public TeamsConfiguration(String playerBalance, TeamChooserType type, String[] teamNames, DyeColor[] colors) {
		this.type = type;
		teams = new ArrayList<>();
		parsePlayerBalance(playerBalance);
		for (int i = 0; i < teamNames.length; i++) {
			String teamName = teamNames[i];
			teams.get(i).setName(teamName);
		}

		for (int i = 0;i<colors.length;i++){
			teams.get(i).setTeamColor(colors[i]);
		}
	}

	public TeamsConfiguration setTeamsData(TeamsData data, int... teamNumbers){
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

	public void addPlayerToTeam(Player player, Team team){
		teamsOfPlayers.put(player.getUniqueId(), team);
		team.addPlayer(player);
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

}
