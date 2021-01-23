package tests.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import tests.shops.SmallShop;

public class InteractListener implements Listener {


    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getItem().getType().equals(Material.IRON_AXE)){
            new SmallShop().openShop(e.getPlayer());
        }
    }

}
