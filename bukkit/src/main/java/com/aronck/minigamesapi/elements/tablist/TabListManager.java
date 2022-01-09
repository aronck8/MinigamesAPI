package com.aronck.minigamesapi.elements.tablist;

import com.aronck.minigamesapi.minigame.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TabListManager {

    private Minigame minigame;

    private TablistSetting tablistSetting;

    public TabListManager(Minigame minigame) {
        this.minigame = minigame;
    }

    public TablistSetting getTablistSetting() {
        return tablistSetting;
    }

    public void setTablistSetting(TablistSetting tablistSetting) {
        this.tablistSetting = tablistSetting;
    }

    public void renderTablist(){
        if(tablistSetting==null)return;
        /*for(Player player : Bukkit.getOnlinePlayers()){
            if(minigame.getTeamOfPlayer(player)!=null){
                tablistSetting.renderPlayer(player, minigame.getTeamOfPlayer(player));
            }
        }*/
    }
}
