package com.aronck.minigamesapi.minigame;

import com.aronck.minigamesapi.elements.countDown.StandardCountdown;
import com.aronck.minigamesapi.elements.event.EventsManager;
import com.aronck.minigamesapi.elements.locations.LocationChooser;
import com.aronck.minigamesapi.elements.map.GameMap;
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
	AbstractState cleanUpState = new PostGameCleanupState(this);

	JavaPlugin main;

	GameMap currentMap;

	ItemStack locationChooserItem = new ItemStack(Material.COMPASS);
	ItemStack teamChooserItem = new ItemStack(Material.STONE);

	EventsManager eventsManager;
	TabListManager tabListManager;

	List<LocationChooser> locationChoosers;
	List<GameMap> maps;

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
		tabListManager = new TabListManager(this);
		locationChoosers = new ArrayList<>();
		maps = new ArrayList<>();

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

		boolean canStart = currentMap.getStartConditions().stream().allMatch(Conditional::evaluate);
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
		if(getTeamsConfiguration()!=null) {
			for (Team team : getTeamsConfiguration().getTeams()) {

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
			return;
		}

		currentState = nextState;
		nextState = currentState.nextState;
		currentState.start();
	}

	public void stopMinigame(){
		if(currentState!=lastState){
			currentState.stop();
			cleanUpState.start();
		}
	}

	void preInitGameStates(){
		AbstractState state = firstState;
		while(state!=null){
			state.preInit();
			state = state.nextState;
		}
	}

	public boolean isIngamePhase(){
		return currentState.gamePhase.equals(GamePhase.INGAME);
	}

	public AbstractState getCurrentState(){
		return currentState;
	}

	public TeamsConfiguration getTeamsConfiguration(){
		return currentMap.getTeamsConfiguration();
	}

	public void selectCurrentMap(GameMap map){
		currentMap = map;
	}

	public void selectCurrentMap(String name){
		for(GameMap map : maps){
			if(map.getName().equals(name))currentMap = map;
		}
	}

	public GameMap getCurrentMap(){
		return currentMap;
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
		if(currentMap!=null) return currentMap.getTeamsConfiguration().getTeamOfPlayer(player);
		return null;
	}

	public EventsManager getEventsManager() {
		return eventsManager;
	}

	public int getID() {
		return ID;
	}

}
