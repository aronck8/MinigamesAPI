package com.aronck.minigamesapi.elements.shop;

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

public abstract class SimpleShop<K, V> extends Shop<K, V>{

    /**
     * The different products with their individual prices
     */
    protected List<ShopElement<K, V>> products = new ArrayList<>();

    /**
     * HashMap used to create a link between a buyable and its symbol in the menu
     */
    protected HashMap<ItemStack, ShopElement<K, V>> shopSymbolsForObject = new HashMap<>();

    /**
     * creates a new Shop object with a given name and row count
     *
     * @param name the name of the shop
     * @param rows the amount of rows the shop has
     */
    public SimpleShop(String name, int rows) {
        super(name, rows);
    }

    public void addProduct(ShopElement<K, V> product){
        products.add(product);
        shopSymbolsForObject.put(product.getItemInShopMenu(), product);
    }

    @Override
    public Inventory generateShopInventory() {
        Inventory inv = Bukkit.createInventory(null, PluginUtils.convertToMultiplesOfBase(products.size(), 9), name);

        int index = 0;
        for(ShopElement<K, V> product : products){
            inv.setItem(index++, getItemStack(product));
        }
        return inv;
    }

    @Override
    public void handleItemClicked(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        if(item==null || Material.AIR.equals(item.getType()))return;

        //transforming the clicked item(shopItem) to the actual buyable item
        ShopElement<K, V> itemInShop = shopSymbolsForObject.get(item);
        if(!products.contains(itemInShop))return;

        buy0(itemInShop, (Player) e.getWhoClicked());
        e.setCancelled(true);
    }

}
