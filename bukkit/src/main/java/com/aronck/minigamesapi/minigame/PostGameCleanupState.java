package com.aronck.minigamesapi.minigame;

public class PostGameCleanupState extends AbstractState{

    protected PostGameCleanupState(Minigame minigame) {
        super(minigame, "cleanup", GamePhase.POSTGAME, true);
    }

    @Override
    protected void preInit() {

    }

    @Override
    protected void onStart() {
        stop();
    }

    @Override
    protected void onStop() {

    }

    @Override
    protected void cleanUp() {

    }
}
