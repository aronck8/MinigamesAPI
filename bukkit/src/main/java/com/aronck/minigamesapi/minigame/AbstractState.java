package com.aronck.minigamesapi.minigame;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractState {

    protected Minigame minigame;
    protected JavaPlugin main;
    protected final String NAME;
    protected final boolean isActiveState;
    protected AbstractState nextState;
    protected boolean startNextStateOnFinish = true;
    protected boolean running;

    protected AbstractState(Minigame minigame, String name, boolean isActiveState) {
        this.minigame = minigame;
        main = minigame.main;
        NAME = name;
        this.isActiveState = isActiveState;
    }

    protected AbstractState(Minigame minigame, String name, boolean isActiveState, boolean startNextStateOnFinish) {
        this.minigame = minigame;
        main = minigame.main;
        NAME = name;
        this.isActiveState = isActiveState;
        this.startNextStateOnFinish = startNextStateOnFinish;
    }

    protected AbstractState(String name, boolean isActiveState, boolean startNextStateOnFinish) {
        NAME = name;
        this.isActiveState = isActiveState;
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
