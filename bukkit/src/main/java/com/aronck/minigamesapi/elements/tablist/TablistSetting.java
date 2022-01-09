package com.aronck.minigamesapi.elements.tablist;

import com.aronck.minigamesapi.elements.teams.Team;
import org.bukkit.entity.Player;

public abstract class TablistSetting {

    public abstract String renderPlayer(Player player, Team team);

}
