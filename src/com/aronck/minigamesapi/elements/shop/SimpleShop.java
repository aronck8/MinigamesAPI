package com.aronck.minigamesapi.elements.shop;

import com.aronck.minigamesapi.utils.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public abstract class SimpleShop<K, V> extends Shop<K, V>{

    /**
     * The different products with their individual prices
     */
    private HashMap<K, V> priceOfProduct = new HashMap<>();

    /**
     * HashMap used to create a link between a buyable and its symbol in the menu
     */
    private HashMap<ItemStack, K> shopSymbolsForObject = new HashMap<>();

    /**
     * creates a new Shop object with a given name and row count
     *
     * @param name
     * @param rows
     */
    public SimpleShop(String name, int rows) {
        super(name, rows);
    }

    public SimpleShop<K, V> addProduct(K product, V price){
        priceOfProduct.put(product, price);
        shopSymbolsForObject.put(getItemStack(product, price), product);
        return this;
    }

    @Override
    public void openShop(Player player) {
        Inventory inv = Bukkit.createInventory(null, PluginUtils.convertToMultiplesOfBase(priceOfProduct.size(), 9), name);

        int index = 0;
        for(K key : priceOfProduct.keySet()){
            inv.setItem(index++, getItemStack(key, priceOfProduct.get(key)));
        }
        player.openInventory(inv);
    }

    @Override
    public void handleItemClicked(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        if(item==null || item.getType()==null || Material.AIR.equals(item.getType()))return;

        //transforming the clicked item(shopItem) to the actual buyable item
        K itemInShop = shopSymbolsForObject.get(item);
        if(!priceOfProduct.containsKey(itemInShop))return;

        buy0(itemInShop, priceOfProduct.get(itemInShop), (Player) e.getWhoClicked());
        e.setCancelled(true);
    }

}
