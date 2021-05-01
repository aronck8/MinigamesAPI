package com.aronck.minigamesapi.minigame;

import com.aronck.minigamesapi.elements.teams.TeamsConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class MinigameBuilder extends MinigameBuilderStage {

    public MinigameBuilder(JavaPlugin main) {
        super(new Minigame(main));
    }

    public BuilderStageTwo setTeams(TeamsConfiguration teamsConfiguration) {
        minigame.teamsConfiguration = teamsConfiguration;
        return new BuilderStageTwo(minigame);
    }
}
