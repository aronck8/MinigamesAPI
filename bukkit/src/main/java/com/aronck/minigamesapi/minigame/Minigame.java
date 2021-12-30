package com.aronck.minigamesapi.minigame;

import com.aronck.minigamesapi.commands.internalCommands.LocationChooserCommand;
import com.aronck.minigamesapi.commands.internalCommands.MinigameSetupCommand;
import com.aronck.minigamesapi.elements.countDown.StandartCountDown;
import com.aronck.minigamesapi.elements.event.EventsManager;
import com.aronck.minigamesapi.elements.locations.LocationChooser;
import com.aronck.minigamesapi.elements.map.MapConfiguration;
import com.aronck.minigamesapi.elements.scheduler.SpawnerManager;
import com.aronck.minigamesapi.elements.tablist.TabListManager;
import com.aronck.minigamesapi.elements.teams.Conditional;
import com.aronck.minigamesapi.elements.teams.Team;
import com.aronck.minigamesapi.elements.teams.TeamsConfiguration;
import com.aronck.minigamesapi.events.custom.GameOverEvent;
import com.aronck.minigamesapi.events.internalEvents.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import tests.Main;

import java.util.ArrayList;
import java.util.List;

public class Minigame {

	private final int ID;
	private final String NAME;
	private final int STANDART_COUNTDOWN_TIME = 5;

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

	CountDown countDown = new StandartCountDown(30);

	private int taskId;

	Minigame(JavaPlugin main) {
		this(main, "Minigame");
	}

	Minigame(JavaPlugin main, String name){
		ID = Minigames.getNextId();
		this.NAME = name;
		//make sure the API itself is enabled before it gets used by another plugin
		Plugin plugin = Bukkit.getPluginManager().getPlugin("MinigamesAPI");
		if(plugin==null) try {
			throw new InvalidPluginException("Couldn't load the MinigamesAPI");
		} catch (InvalidPluginException e) {
			e.printStackTrace();
			return;
		}
		if(!plugin.isEnabled()){
			Bukkit.getPluginManager().enablePlugin(plugin);
		}
		this.main = main;
		eventsManager = new EventsManager(main);
		spawnerManager = new SpawnerManager(main);
		tabListManager = new TabListManager(this);
		startConditions = new ArrayList<>();
		locationChoosers = new ArrayList<>();

		Bukkit.getPluginManager().registerEvents(eventsManager, main);

		Main.getInstance().getCommand("locations").setExecutor(new LocationChooserCommand());
		Main.getInstance().getCommand("minigame").setExecutor(new MinigameSetupCommand());

		Minigames.addMinigame(this);
	}

	void registerListeners(){
		Bukkit.getPluginManager().registerEvents(new InteractEvent(this), main);
		Bukkit.getPluginManager().registerEvents(new InventoryClick(this), main);
		Bukkit.getPluginManager().registerEvents(new JoinEvent(this), main);
		Bukkit.getPluginManager().registerEvents(new DeathListener(this), main);
		Bukkit.getPluginManager().registerEvents(new RespawnEvent(this), main);
		Bukkit.getPluginManager().registerEvents(new BlockBreakListener(this), main);
		Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(this), main);
		eventsManager.registerListeners();
	}

	/**
	 *
	 * inititalizes the minigame before start
	 */
	void initMiniGame(){

	}

	/**
	 *
	 * starts the main minigame after the init and countdown phase
	 */
	void startMiniGame(){

		spawnerManager.startSpawners();

		if (mapConfiguration != null) mapConfiguration.fillChests();
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
	void checkConditionsAndStartGame(){

		boolean canStart = startConditions.stream().allMatch(Conditional::evaluate);
		if(canStart){
			startCountdown(STANDART_COUNTDOWN_TIME);
		}

	}

	public void startCountdown(){
		countDown.start0(this);
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
		initMiniGame();
		countDown.start0(this, time);
	}

	void startPostStartTask(){
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, this::checkConditionsAndEndGame, 0, 20);
	}

	/**
	 *
	 * checks the conditions to see if the countdown should start
	 *
	 * @see #startCountdown(int)
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

	public void stopMinigame(){
		spawnerManager.stopSpawners();
		if(mapConfiguration!=null)mapConfiguration.resetMap();
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
