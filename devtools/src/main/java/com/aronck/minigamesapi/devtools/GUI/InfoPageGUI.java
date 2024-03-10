package com.aronck.minigamesapi.devtools.GUI;

import com.aronck.minigamesapi.devtools.DToolController;
import com.aronck.minigamesapi.devtools.DataFetchers;
import com.aronck.minigamesapi.minigame.AbstractState;
import com.aronck.minigamesapi.minigame.Minigame;
import com.aronck.minigamesapi.utils.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

public final class InfoPageGUI extends ConfigGUI{

    private DataFetchers dataFetchers;
    private PhasesGUI phasesGUI;

    private HashMap<ItemStack, BiConsumer<Player, ItemStack>> itemClickActions = new HashMap<>();

    public InfoPageGUI(Minigame minigame, DataFetchers dataFetchers, MainConfigGUI mainConfigGUI) {
        super(PluginUtils.getItem(Material.OAK_SIGN,1 , "Allgemeine Infos"), DToolController.DEBUG_INFOS_MENU_NAME, minigame, mainConfigGUI, mainConfigGUI);
        this.dataFetchers = dataFetchers;
        phasesGUI = new PhasesGUI(minigame, dataFetchers, this);
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent event) {
        System.out.println(itemClickActions.containsKey(event.getCurrentItem()));
        if(itemClickActions.containsKey(event.getCurrentItem()))itemClickActions.get(event.getCurrentItem()).accept((Player) event.getWhoClicked(), event.getCurrentItem());
    }

    @Override
    public Inventory generateInventory() {
        Inventory inv = Bukkit.createInventory(null, 36, inventoryTitle);

        inv.setItem(0, generateMinigameStatesInfos());
        return inv;
    }

    private ItemStack generateMinigameStatesInfos(){
        List<AbstractState> states = dataFetchers.getMinigameDataFetcher().getStates(minigame);


        ItemStack item = PluginUtils.getItem(Material.OAK_SIGN, 1, "§aAktuelle Phase: §2"
                + minigame.getCurrentState().getGamePhase(), states.stream().map(state -> (state.isRunning()?"§2":"§c") + state.getNAME()).toArray(String[]::new));

        itemClickActions.put(item, (player, itemStack) -> phasesGUI.openInventory(player));
        return item;
    }
}
