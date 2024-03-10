package com.aronck.minigamesapi.events.internalEvents;

import com.aronck.minigamesapi.elements.teams.Team;
import com.aronck.minigamesapi.elements.teams.TeamsConfiguration;
import com.aronck.minigamesapi.minigame.Minigame;
import com.aronck.minigamesapi.elements.locations.Location;
import com.aronck.minigamesapi.elements.locations.LocationChooser;
import com.aronck.minigamesapi.elements.shop.Shops;
import com.aronck.minigamesapi.utils.config.LocationsConfig;
import com.aronck.minigamesapi.utils.PluginConstants;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public final class InventoryClickListener implements Listener {

	private Minigame minigame;

	public InventoryClickListener(Minigame minigame){
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
				player.sendMessage("Die Position für: \"" + chooser.getKey() + "\" wurde gesetzt!");
				LocationsConfig.saveLocation(new Location(chooser.getKey(), player.getLocation()));
			}
		}else if(title.equals(PluginConstants.TEAM_CHOOSER_INVENTORY_NAME)){
			e.setCancelled(true);
			TeamsConfiguration teamsConfiguration = minigame.getCurrentMap().getTeamsConfiguration();
			Team team = teamsConfiguration.getTeamFromItem(e.getCurrentItem());

			if(team == null){
				player.sendMessage("§eDas Team konnte nicht gefunden werden. Kontaktieren Sie einen Administrator!");
				return;
			}

			if(team.equals(teamsConfiguration.getTeamOfPlayer(player))){
				player.sendMessage("§cDu bist bereits in dem Team!");
				return;
			}

			if(!team.hasFreeSlots()){
				player.sendMessage("§cDieses Team ist bereits voll!");
				return;
			}

			player.sendMessage("§eDu wurdest dem Team erfolgreich hinzugefügt");
			teamsConfiguration.addPlayerToTeam(player, team, minigame.isIngamePhase());

			//reopen the inventory to update items that may have changed
			teamsConfiguration.openTeamsChooserInventory(player);

		}

		//try to process the clickEvent via the shop system
		Shops.processClickEvent(e);
	}

}
