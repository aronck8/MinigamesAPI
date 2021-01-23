package com.aronck.minigamesapi.elements.tablist;

import com.aronck.minigamesapi.elements.teams.Team;
import org.bukkit.entity.Player;

public class StandartTablistSetting extends TablistSetting{

    @Override
    public String renderPlayer(Player player, Team team) {
        return team.getTeamColor().getColor() + player.getDisplayName();
    }

    @Override
    public String renderTeamlessPlayer(Player player) {
        return null;
    }
}
