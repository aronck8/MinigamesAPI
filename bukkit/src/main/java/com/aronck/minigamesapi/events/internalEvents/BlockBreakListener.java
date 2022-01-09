package com.aronck.minigamesapi.events.internalEvents;

import com.aronck.minigamesapi.minigame.Minigame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    private Minigame minigame;

    public BlockBreakListener(Minigame minigame) {
        this.minigame = minigame;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        if(minigame.getCurrentMap()!=null) minigame.getCurrentMap().processBlockBreakEvent(event);
    }

}
