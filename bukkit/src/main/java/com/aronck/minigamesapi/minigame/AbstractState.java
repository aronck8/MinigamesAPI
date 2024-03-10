package com.aronck.minigamesapi.minigame;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public abstract class AbstractState {

    protected Minigame minigame;
    protected JavaPlugin main;
    protected final String NAME;
    protected final GamePhase gamePhase;
    protected AbstractState nextState;
    protected boolean startNextStateOnFinish = true;
    protected boolean running = false;

    protected ArrayList<Listener> stateDependantListeners = new ArrayList<>();

    protected AbstractState(String name, GamePhase gamePhase) {
        NAME = name;
        this.gamePhase = gamePhase;
    }

    protected AbstractState(String name, GamePhase gamePhase, boolean startNextStateOnFinish) {
        NAME = name;
        this.gamePhase = gamePhase;
        this.startNextStateOnFinish = startNextStateOnFinish;
    }

    AbstractState(Minigame minigame, String name, GamePhase gamePhase) {
        this.minigame = minigame;
        main = minigame.main;
        NAME = name;
        this.gamePhase = gamePhase;
    }

    AbstractState(Minigame minigame, String name, GamePhase gamePhase, boolean startNextStateOnFinish) {
        this.minigame = minigame;
        main = minigame.main;
        NAME = name;
        this.gamePhase = gamePhase;
        this.startNextStateOnFinish = startNextStateOnFinish;
    }

    void setMinigame(Minigame minigame){
        this.minigame = minigame;
        main = minigame.main;
    }

    public AbstractState setNextState(AbstractState nextState){
        //ensure, that if the next state isn't bound to a minigame, to bind it to the current minigame
        if(nextState.minigame==null){
            nextState.minigame=minigame;
        }
        this.nextState = nextState;
        return nextState;
    }

    public AbstractState getNextState() {
        return nextState;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }

    public String getNAME() {
        return NAME;
    }

    public boolean isStartNextStateOnFinish() {
        return startNextStateOnFinish;
    }

    public AbstractState addStateDependantListener(Listener listener){
        stateDependantListeners.add(listener);
        if(running)Bukkit.getPluginManager().registerEvents(listener, main);
        return this;
    }

    public void start(){
        if(running)throw new UnsupportedOperationException("GameState already running!");
        running = true;
        stateDependantListeners.forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, main));
        onStart();
    }

    public void stop(){
        running = false;
        stateDependantListeners.forEach(HandlerList::unregisterAll);
        onStop();
        if(startNextStateOnFinish)minigame.startNextState();
    }

    public boolean isRunning() {
        return running;
    }

    protected abstract void preInit();

    protected abstract void onStart();

    protected abstract void onStop();

    protected abstract void cleanUp();

}
