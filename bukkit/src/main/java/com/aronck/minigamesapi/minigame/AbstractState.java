package com.aronck.minigamesapi.minigame;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractState {

    protected Minigame minigame;
    protected JavaPlugin main;
    protected final String NAME;
    protected final GamePhase gamePhase;
    protected AbstractState nextState;
    protected boolean startNextStateOnFinish = true;
    protected boolean running = false;

    protected AbstractState(Minigame minigame, String name, GamePhase gamePhase) {
        this.minigame = minigame;
        main = minigame.main;
        NAME = name;
        this.gamePhase = gamePhase;
    }

    protected AbstractState(Minigame minigame, String name, GamePhase gamePhase, boolean startNextStateOnFinish) {
        this.minigame = minigame;
        main = minigame.main;
        NAME = name;
        this.gamePhase = gamePhase;
        this.startNextStateOnFinish = startNextStateOnFinish;
    }

    protected AbstractState(String name, GamePhase gamePhase, boolean startNextStateOnFinish) {
        NAME = name;
        this.gamePhase = gamePhase;
        this.startNextStateOnFinish = startNextStateOnFinish;
    }

    void setMinigame(Minigame minigame){
        this.minigame = minigame;
        main = minigame.main;
    }

    public void setNextState(AbstractState nextState){
        //ensure, that if the next state isn't bound to a minigame, to bind it to the current minigame
        if(nextState.minigame==null){
            nextState.minigame=minigame;
        }
        this.nextState = nextState;
    }

    public AbstractState getNextState() {
        return nextState;
    }

    public void start(){
        running = true;
        onStart();
    }

    public void stop(){
        running = false;
        onStop();
        System.out.println(nextState + " next in state");
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
