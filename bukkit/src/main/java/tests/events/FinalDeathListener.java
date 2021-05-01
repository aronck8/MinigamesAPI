package tests.events;

import com.aronck.minigamesapi.events.custom.FinalDeathEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FinalDeathListener implements Listener {

    @EventHandler
    public void onFinalDeath(FinalDeathEvent e){
        e.getPlayerDeathEvent().getEntity().sendMessage("Ha! rip");
    }

}
