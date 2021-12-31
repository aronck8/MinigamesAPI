package com.aronck.minigamesapi.minigame;

import com.aronck.minigamesapi.events.custom.MinigameStartEvent;
import org.bukkit.Bukkit;

public class LobbyState extends AbstractState{

    public LobbyState(Minigame minigame) {
        super(minigame, "lobby", false);
    }

    public LobbyState() {
        super("lobby", false, false);
    }

    @Override
    protected void preInit() {
    }

    @Override
    protected void onStart() {
        minigame.startPreStartTask();
    }

    @Override
    protected void onStop() {

    }

    @Override
    protected void cleanUp() {

    }
}
