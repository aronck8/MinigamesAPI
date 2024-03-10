package com.aronck.minigamesapi.devtools.GUI;

import com.aronck.minigamesapi.devtools.DToolController;
import com.aronck.minigamesapi.minigame.Minigame;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

abstract class ConfigGUI {

    protected MainConfigGUI mainConfig;
    protected ConfigGUI parent;

    protected Minigame minigame;

    protected ItemStack inventoryItem;
    protected String inventoryTitle;

    //require main to allow only the mainConfigGui to reigster itself via this constructor
    public ConfigGUI(ItemStack inventoryItem, String inventoryTitle, Minigame minigame, DToolController main) {
        this.inventoryItem = inventoryItem;
        this.inventoryTitle = inventoryTitle;
        this.minigame = minigame;
    }

    public ConfigGUI(ItemStack inventoryItem, String inventoryTitle, Minigame minigame, MainConfigGUI mainConfigGUI, ConfigGUI parent) {
        this.inventoryItem = inventoryItem;
        this.inventoryTitle = inventoryTitle;
        this.minigame = minigame;
        this.mainConfig = mainConfigGUI;
        this.parent = parent;
    }

    public abstract Inventory generateInventory();

    public abstract void handleInventoryClick(InventoryClickEvent event);

    public void openInventory(Player player){
        Inventory inventory = generateInventory();
        for(ItemStack item : inventory.getContents()){
            mainConfig.registerUsedItem(item, this);
        }
        player.openInventory(inventory);
    }

    public ItemStack getInventoryItem() {
        return inventoryItem;
    }

    public String getInventoryTitle() {
        return inventoryTitle;
    }

    public MainConfigGUI getMainConfig() {
        return mainConfig;
    }
}
