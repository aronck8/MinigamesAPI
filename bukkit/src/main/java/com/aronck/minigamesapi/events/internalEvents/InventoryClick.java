package com.aronck.minigamesapi.events.internalEvents;

import com.aronck.minigamesapi.elements.teams.Team;
import com.aronck.minigamesapi.elements.teams.TeamsConfiguration;
import com.aronck.minigamesapi.minigame.Minigame;
import com.aronck.minigamesapi.elements.locations.Location;
import com.aronck.minigamesapi.elements.locations.LocationChooser;
import com.aronck.minigamesapi.elements.shop.Shops;
import com.aronck.minigamesapi.utils.Config;
import com.aronck.minigamesapi.utils.PluginConstants;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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
		if(!(e.getWhoClicked() instanceof Player)) return;
		Player player = (Player)e.getWhoClicked();
		if(e.getClickedInventory()==null)return;
		String title = e.getView().getTitle();
		if(title.equals(PluginConstants.LOCATION_CHOOSER_INVENTORY_NAME)){
			e.setCancelled(true);
			for(LocationChooser chooser : minigame.getLocationChoosers()){
				if(!chooser.getItem().equals(e.getCurrentItem()))continue;
				e.getWhoClicked().sendMessage("Die Position für: \"" + chooser.getKey() + "\" wurde gesetzt!");
				Config.saveLocation(new Location(chooser.getKey(), e.getWhoClicked().getLocation()));
			}
		}else if(title.equals(PluginConstants.TEAM_CHOOSER_INVENTORY_NAME)){
			e.setCancelled(true);
			TeamsConfiguration teamsConfiguration = minigame.getCurrentMap().getTeamsConfiguration();
			Team team = teamsConfiguration.getTeamFromItem(e.getCurrentItem());
			if(team==null || !team.hasFreeSlots()){
				e.getWhoClicked().sendMessage("§cDieses Team ist bereits voll!");
				return;
			}

			teamsConfiguration.addPlayerToTeam(player, team, minigame.isIngamePhase());

		}

		//try to process the clickEvent via the shop system
		Shops.processClickEvent(e);
	}

}
