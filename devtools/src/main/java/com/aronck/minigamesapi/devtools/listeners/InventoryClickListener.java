package com.aronck.minigamesapi.devtools.listeners;

import com.aronck.minigamesapi.devtools.DToolController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    private DToolController main;

    public InventoryClickListener(DToolController main) {
        this.main = main;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(main.getMainConfigGUI()==null)return;
        main.getMainConfigGUI().handleInventoryClick(event);
    }
}
