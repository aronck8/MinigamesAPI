package com.aronck.minigamesapi.devtools.GUI;

import com.aronck.minigamesapi.devtools.DToolController;
import com.aronck.minigamesapi.devtools.DataFetchers;
import com.aronck.minigamesapi.minigame.Minigame;
import com.aronck.minigamesapi.utils.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainConfigGUI extends ConfigGUI{

    protected DToolController main;
    private Minigame minigame;

    private List<ConfigGUI> configPages = new ArrayList<>();

    private HashMap<ItemStack, ConfigGUI> menuItemToConfig = new HashMap<>();

    private Map<ItemStack, ConfigGUI> usedItems = new HashMap<>();

    private Map<String, ConfigGUI> inventoryTitles = new HashMap<>();

    public MainConfigGUI(DToolController main, Minigame minigame, DataFetchers dataFetchers) {
        super(null, DToolController.DEBUG_MENU_NAME, minigame, main);
        this.main = main;
        this.minigame = minigame;
        mainConfig = this;
        registerConfigPage(new InfoPageGUI(minigame, dataFetchers, this));
        registerConfigPage(new TeamConfigGUI(minigame, this));
    }

    public void registerConfigPage(ConfigGUI configGUI){
        configPages.add(configGUI);
        menuItemToConfig.put(configGUI.getInventoryItem(), configGUI);
        inventoryTitles.put(configGUI.getInventoryTitle(), configGUI);
    }

    void registerUsedItem(ItemStack item, ConfigGUI configGUI){
        usedItems.put(item, configGUI);
    }

    @Override
    public void openInventory(Player player){
        player.openInventory(generateInventory());
    }

    @Override
    public Inventory generateInventory() {
        Inventory inv = Bukkit.createInventory(null, 36, DToolController.DEBUG_MENU_NAME);

        for (int i = 0; i < configPages.size(); i++) {
            ConfigGUI config = configPages.get(i);
            inv.setItem(i, config.getInventoryItem());
        }

        return inv;

    }

    @Override
    public void handleInventoryClick(InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player))return;
        if(usedItems.containsKey(event.getCurrentItem()))event.setCancelled(true);
        ConfigGUI config;
        if((config = menuItemToConfig.get(event.getCurrentItem())) != null){
            config.openInventory((Player)event.getWhoClicked());
            event.setCancelled(true);
        }else if(usedItems.containsKey(event.getCurrentItem())){
            event.setCancelled(true);
        }

        if(inventoryTitles.containsKey(event.getView().getTitle())){
            inventoryTitles.get(event.getView().getTitle()).handleInventoryClick(event);
        }
    }
}
