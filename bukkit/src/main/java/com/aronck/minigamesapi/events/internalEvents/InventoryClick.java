package com.aronck.minigamesapi.events.internalEvents;

import com.aronck.minigamesapi.minigame.Minigame;
import com.aronck.minigamesapi.elements.locations.Location;
import com.aronck.minigamesapi.elements.locations.LocationChooser;
import com.aronck.minigamesapi.elements.shop.Shops;
import com.aronck.minigamesapi.utils.Config;
import com.aronck.minigamesapi.utils.PluginConstants;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public final class InventoryClick implements Listener {

	private Minigame minigame;

	public InventoryClick(Minigame minigame){
		this.minigame = minigame;
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e){
		if(e.getClickedInventory()==null)return;
		String title = e.getClickedInventory().getTitle();
		if(title.equals(PluginConstants.LOCATION_CHOOSER_INVENTORY_NAME)){
			e.setCancelled(true);
			for(LocationChooser chooser : minigame.getLocationChoosers()){
				if(!chooser.getItem().equals(e.getCurrentItem()))continue;
				e.getWhoClicked().sendMessage("Die Position für: \"" + chooser.getKey() + "\" wurde gesetzt!");
				Config.saveLocation(new Location(chooser.getKey(), e.getWhoClicked().getLocation()));
			}
		}

		//try to process the clickEvent via the shop system
		Shops.processClickEvent(e);
	}

}
