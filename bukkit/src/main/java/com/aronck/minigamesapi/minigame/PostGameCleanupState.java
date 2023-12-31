package com.aronck.minigamesapi.minigame;

public class PostGameCleanupState extends AbstractState{

    PostGameCleanupState(Minigame minigame) {
        super(minigame, "cleanup", GamePhase.POSTGAME, false);
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
