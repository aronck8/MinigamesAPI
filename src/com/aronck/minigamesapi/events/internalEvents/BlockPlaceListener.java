package com.aronck.minigamesapi.events.internalEvents;

import com.aronck.minigamesapi.minigame.Minigame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    private Minigame minigame;

    public BlockPlaceListener(Minigame minigame) {
        this.minigame = minigame;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        if(minigame.getMapConfiguration()!=null) minigame.getMapConfiguration().handleBlockPlaced(event);
    }
}
