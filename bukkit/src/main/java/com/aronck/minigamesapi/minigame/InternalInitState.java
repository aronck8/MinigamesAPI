package com.aronck.minigamesapi.minigame;

import com.aronck.minigamesapi.commands.internalCommands.LocationChooserCommand;
import com.aronck.minigamesapi.commands.internalCommands.MinigameSetupCommand;
import com.aronck.minigamesapi.events.internalEvents.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import tests.Main;

public class InternalInitState extends AbstractState{

    public InternalInitState(Minigame minigame) {
        super(minigame, "init", GamePhase.PREGAME, true);
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
        registerListeners();
    }

    @Override
    protected void onStart() {
        //directly continue to the next state which is the first user defined state
        stop();
    }

    @Override
    protected void onStop() {

    }

    @Override
    protected void cleanUp(){}

    private void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new InteractEvent(minigame), main);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(minigame), main);
        Bukkit.getPluginManager().registerEvents(new DeathListener(minigame), main);
        Bukkit.getPluginManager().registerEvents(new RespawnEvent(minigame), main);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(minigame), main);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(minigame), main);
        minigame.eventsManager.registerListeners();

        Bukkit.getPluginManager().registerEvents(minigame.eventsManager, main);

        Main.getInstance().getCommand("locations").setExecutor(new LocationChooserCommand());
        Main.getInstance().getCommand("minigame").setExecutor(new MinigameSetupCommand());
    }
}
