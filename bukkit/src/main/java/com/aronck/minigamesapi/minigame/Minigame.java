package com.aronck.minigamesapi.minigame;

import com.aronck.minigamesapi.elements.countDown.StandardCountdown;
import com.aronck.minigamesapi.elements.event.EventsManager;
import com.aronck.minigamesapi.elements.locations.LocationChooser;
import com.aronck.minigamesapi.elements.map.MapConfiguration;
import com.aronck.minigamesapi.elements.scheduler.SpawnerManager;
import com.aronck.minigamesapi.elements.tablist.TabListManager;
import com.aronck.minigamesapi.elements.teams.Conditional;
import com.aronck.minigamesapi.elements.teams.Team;
import com.aronck.minigamesapi.elements.teams.TeamsConfiguration;
import com.aronck.minigamesapi.events.custom.GameOverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Minigame {

	private final int ID;
	private final String NAME;
	private final int STANDART_COUNTDOWN_TIME = 5;
	AbstractState firstState;
	AbstractState currentState;
	AbstractState nextState;
	AbstractState lastState;

	JavaPlugin main;

	TeamsConfiguration teamsConfiguration;
	MapConfiguration mapConfiguration;

	ItemStack locationChooserItem = new ItemStack(Material.COMPASS);
	ItemStack teamChooserItem = new ItemStack(Material.WOOL);

	EventsManager eventsManager;
	SpawnerManager spawnerManager;
	TabListManager tabListManager;

	List<Conditional> startConditions;
	List<LocationChooser> locationChoosers;

	Countdown countDown = new StandardCountdown(30);

	private int taskId;

	Minigame(JavaPlugin main) {
		this(main, "Minigame");
	}

	Minigame(JavaPlugin main, String name){

		ID = Minigames.getNextId();
		this.NAME = name;
		this.main = main;

		firstState = new InternalInitState(this);
		nextState = firstState;
		lastState = nextState;
		eventsManager = new EventsManager(main);
		spawnerManager = new SpawnerManager(main);
		tabListManager = new TabListManager(this);
		startConditions = new ArrayList<>();
		locationChoosers = new ArrayList<>();

		Minigames.addMinigame(this);
	}

	public void start(){
		startNextState();
	}

	/**
	 *
	 * starts the main minigame after the init and countdown phase
	 */
	void startMiniGame(){

		spawnerManager.startSpawners();

		System.out.println("starting Minigame");
		//teleport players to their start position
		if(teamsConfiguration!=null) {
			for (Team team : teamsConfiguration.getTeams()) {
				for (Player player : team.getPlayers()) {
					if(team.getData().getRandomRespawnLocationFromList()!=null)
						player.teleport(team.getData().getRandomRespawnLocationFromList());
					team.getData().getRandomKitFromList().applyToPlayer(player);
				}
			}
		}

		startPostStartTask();
	}

	/**
	 *
	 * starts the timer that checks if the countdown should start
	 * @see #checkConditionsAndStartGame()
	 */
	void startPreStartTask() {
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, this::checkConditionsAndStartGame, 0, 20);
	}

	/**
	 *
	 * checks the conditions to see if the countdown should start
	 *
	 * @see #startCountdown(int) 
	 */
	private void checkConditionsAndStartGame(){

		boolean canStart = startConditions.stream().allMatch(Conditional::evaluate);
		if(canStart){
			startCountdown(STANDART_COUNTDOWN_TIME);
		}

	}

	public void startCountdown(){
		countDown.start(this);
	}

	/**
	 *
	 * starts the countdown. gets called automatically by {@link #checkConditionsAndStartGame()}
	 * or manually from an user defined start command, etc.
	 *
	 * @param time the countdown in seconds
	 *
	 * @see #checkConditionsAndStartGame()
	 */
	public void startCountdown(int time) {
		System.out.println("countdown wurde gestartet!");
		Bukkit.getScheduler().cancelTask(taskId);
		countDown.start(this, time);
	}

	void startPostStartTask(){
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, this::checkConditionsAndEndGame, 0, 20);
	}

	/**
	 *
	 * checks the conditions to see if the game should stop
	 *
	 * @see #startPostStartTask()
	 */
	void checkConditionsAndEndGame(){
		if(teamsConfiguration!=null) {
			for (Team team : teamsConfiguration.getTeams()) {

				boolean won = team.getData().getWinConditions().stream().allMatch(Conditional::evaluate);

				if(won){

					GameOverEvent gameOverEvent = new GameOverEvent(this, team);
					Bukkit.getPluginManager().callEvent(gameOverEvent);
					if(!gameOverEvent.isCancelled()){
						stopMinigame();
						Bukkit.getScheduler().cancelTask(taskId);
						return;
					}
				}
			}
		}
	}

	public void startNextState(){

		if (nextState == null) {
			System.out.println("No state");
			return;
		} else {
			System.out.println("currentState: " + nextState.NAME);
		}

		//if a state is currently running, stop it before starting further states
		if(currentState!=null && currentState.isRunning()) {
			currentState.stop();
		}
		//if the last state is reached, stop the minigame
		if(nextState==null){
			stopMinigame();
			return;
		}

		currentState = nextState;
		nextState = currentState.nextState;
		currentState.start();
	}

	public void stopMinigame(){
		spawnerManager.stopSpawners();
		if(mapConfiguration!=null)mapConfiguration.resetMap();
	}

	void preInitGameStates(){
		AbstractState state = firstState;
		while(state!=null){
			state.preInit();
			state = state.nextState;
		}
	}

	public boolean isInActiveState(){
		return currentState.isActiveState;
	}

	public AbstractState getCurrentState(){
		return currentState;
	}

	public TeamsConfiguration getTeamsConfiguration(){
		return teamsConfiguration;
	}

	public MapConfiguration getMapConfiguration(){
		return mapConfiguration;
	}

	public List<LocationChooser> getLocationChoosers(){
		return locationChoosers;
	}

	public ItemStack getLocationChooserItem(){
		return locationChooserItem;
	}

	public ItemStack getTeamChooserItem() {
		return teamChooserItem;
	}

	public Team getTeamOfPlayer(Player player){
		if(teamsConfiguration==null)return null;
		return teamsConfiguration.getTeamOfPlayer(player);
	}

	public EventsManager getEventsManager() {
		return eventsManager;
	}

	public List<Team> getTeams(){
		return teamsConfiguration==null ? new ArrayList<>() : teamsConfiguration.getTeams();
	}

	public int getID() {
		return ID;
	}

}
