package com.aronck.minigamesapi.devtools.GUI;

import com.aronck.minigamesapi.devtools.DToolController;
import com.aronck.minigamesapi.minigame.Minigame;
import com.aronck.minigamesapi.utils.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class TeamConfigGUI extends ConfigGUI{


    public TeamConfigGUI(Minigame minigame, MainConfigGUI mainConfigGUI) {
        super(PluginUtils.getItem(Material.RED_WOOL,"§cDebug Teams"), DToolController.DEBUG_TEAMS_MENU_NAME, minigame, mainConfigGUI, mainConfigGUI);
    }

    @Override
    public Inventory generateInventory() {
        Inventory inv = Bukkit.createInventory(null, 27, inventoryTitle);

        return inv;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent event) {

    }
}
