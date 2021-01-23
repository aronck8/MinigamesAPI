package com.aronck.minigamesapi.events.internalEvents;

import com.aronck.minigamesapi.minigame.Minigame;
import com.aronck.minigamesapi.utils.PluginUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public final class InteractEvent implements Listener {

	private Minigame minigame;

	public InteractEvent(Minigame minigame){
		this.minigame = minigame;
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		if(e.getAction()== Action.RIGHT_CLICK_AIR || e.getAction()==Action.RIGHT_CLICK_BLOCK){
			if(minigame.getLocationChooserItem().equals(e.getItem())){
				e.setCancelled(true);
				PluginUtils.openLocationChooserInventory(minigame, e.getPlayer());
			}
		}
	}

}
