package com.aronck.minigamesapi.minigame;

import com.aronck.minigamesapi.elements.map.GameMap;
import com.aronck.minigamesapi.events.custom.MinigameStartEvent;
import org.bukkit.Bukkit;

public class InGameState extends AbstractState{

    public InGameState(Minigame minigame) {
        super(minigame, "ingame", GamePhase.INGAME);
    }

    public InGameState(boolean startNextOnFinish) {
        super("ingame", GamePhase.INGAME, startNextOnFinish);
    }

    @Override
    protected void preInit() {

    }

    @Override
    protected void onStart() {
        MinigameStartEvent minigameStartEvent = new MinigameStartEvent(minigame);
        Bukkit.getPluginManager().callEvent(minigameStartEvent);
        if(!minigameStartEvent.isCancelled())minigame.startMiniGame();
        System.out.println("starting Minigame");
        //teleport players to their start position
        if(minigame.currentMap!=null) {
            minigame.getCurrentMap().initOnMinigameStart();
        }
    }

    @Override
    protected void onStop() {
        minigame.getCurrentMap().performOnGameStopTasks();
    }

    @Override
    protected void cleanUp() {
        GameMap currentMap = minigame.getCurrentMap();
        if(currentMap !=null) currentMap.resetMap();
    }
}
