package com.aronck.minigamesapi.minigame;

import com.aronck.minigamesapi.commands.internalCommands.LocationChooserCommand;
import com.aronck.minigamesapi.commands.internalCommands.MinigameSetupCommand;
import com.aronck.minigamesapi.events.internalEvents.*;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import com.aronck.minigamesapi.Main;

final class InternalInitState extends AbstractState{

    private InteractListener interactListener;
    private InventoryClickListener inventoryClickListener;
    private DeathListener deathListener;
    private RespawnListener respawnListener;
    private BlockBreakListener blockBreakListener;
    private BlockPlaceListener blockPlaceListener;

    InternalInitState(Minigame minigame) {
        super(minigame, "init", GamePhase.PREGAME, true);
        interactListener = new InteractListener(minigame);
        inventoryClickListener = new InventoryClickListener(minigame);
        deathListener = new DeathListener(minigame);
        respawnListener = new RespawnListener(minigame);
        blockBreakListener = new BlockBreakListener(minigame);
        blockPlaceListener = new BlockPlaceListener(minigame);
    }

    @Override
    protected void preInit() {
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
    }

    @Override
    protected void onStart() {
        registerListeners();
        //directly continue to the next state which is the first user defined state
        stop();
    }

    @Override
    protected void onStop() {

    }

    @Override
    protected void cleanUp(){
        HandlerList.unregisterAll(interactListener);
        HandlerList.unregisterAll(inventoryClickListener);
        HandlerList.unregisterAll(deathListener);
        HandlerList.unregisterAll(respawnListener);
        HandlerList.unregisterAll(blockBreakListener);
        HandlerList.unregisterAll(blockPlaceListener);

        minigame.eventsManager.unregisterEvents();
    }

    private void registerListeners(){
        Bukkit.getPluginManager().registerEvents(interactListener, main);
        Bukkit.getPluginManager().registerEvents(inventoryClickListener, main);
        Bukkit.getPluginManager().registerEvents(deathListener, main);
        Bukkit.getPluginManager().registerEvents(respawnListener, main);
        Bukkit.getPluginManager().registerEvents(blockBreakListener, main);
        Bukkit.getPluginManager().registerEvents(blockPlaceListener, main);
        minigame.eventsManager.registerListeners();

        Bukkit.getPluginManager().registerEvents(minigame.eventsManager, main);

        Main.getInstance().getCommand("locations").setExecutor(new LocationChooserCommand());
        Main.getInstance().getCommand("minigame").setExecutor(new MinigameSetupCommand());
    }
}
