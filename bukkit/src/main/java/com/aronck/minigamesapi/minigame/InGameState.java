package com.aronck.minigamesapi.minigame;

import com.aronck.minigamesapi.events.custom.MinigameStartEvent;
import org.bukkit.Bukkit;

public class InGameState extends AbstractState{

    public InGameState(Minigame minigame) {
        super(minigame, "ingame", true);
    }

    public InGameState(boolean startNextOnFinish) {
        super("ingame", true, startNextOnFinish);
    }

    @Override
    protected void preInit() {
        if (minigame.mapConfiguration != null) minigame.mapConfiguration.fillChests();
    }

    @Override
    protected void onStart() {
        MinigameStartEvent minigameStartEvent = new MinigameStartEvent(minigame);
        Bukkit.getPluginManager().callEvent(minigameStartEvent);
        if(!minigameStartEvent.isCancelled())minigame.startMiniGame();
    }

    @Override
    protected void onStop() {

    }

    @Override
    protected void cleanUp() {
        minigame.mapConfiguration.resetMap();
    }
}
