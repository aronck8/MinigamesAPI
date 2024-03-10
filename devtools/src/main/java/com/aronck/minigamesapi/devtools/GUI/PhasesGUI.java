package com.aronck.minigamesapi.devtools.GUI;

import com.aronck.minigamesapi.devtools.DataFetchers;
import com.aronck.minigamesapi.minigame.AbstractState;
import com.aronck.minigamesapi.minigame.Minigame;
import com.aronck.minigamesapi.utils.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhasesGUI extends ConfigGUI{

    private Minigame minigame;
    private DataFetchers dataFetchers;

    private Map<ItemStack, AbstractState> stateItems = new HashMap<>();

    public PhasesGUI(Minigame minigame, DataFetchers dataFetchers, InfoPageGUI infoPageGUI) {
        super(null, "§2Gamephases", minigame, infoPageGUI.mainConfig, infoPageGUI);
        this.minigame = minigame;
        this.dataFetchers = dataFetchers;
    }

    public Inventory generateInventory() {

        List<AbstractState> states = dataFetchers.getMinigameDataFetcher().getStates(minigame);

        Inventory inv = Bukkit.createInventory(null, PluginUtils.convertToMultiplesOfBase(states.size(), 9));

        for (int i = 0; i < states.size(); i++) {
            AbstractState state = states.get(i);
            ItemStack item = generateItemFromState(state);
            stateItems.put(item, state);
            inv.setItem(i, item);
        }

        return inv;
    }

    private ItemStack generateItemFromState(AbstractState state){
        return PluginUtils.getItem(state.isRunning() ? Material.GREEN_WOOL : Material.RED_WOOL, state.getNAME());
    }

    public void handleInventoryClick(InventoryClickEvent event) {

    }
}
