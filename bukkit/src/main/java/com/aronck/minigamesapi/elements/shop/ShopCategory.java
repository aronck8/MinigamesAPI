package com.aronck.minigamesapi.elements.shop;

import com.aronck.minigamesapi.events.custom.ShopBuyEvent;
import com.aronck.minigamesapi.utils.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 *
 * @param <K>t he type of the buyable objects
 * @param <V> the type of the currency e.g Integer for money or any object as a kind of "exchange"
 *
 */
public class ShopCategory<K, V>{

    /**
     * The name of a shop category which can be used as a unique identifier
     */
    private String name;

    /**
     * the item used in the menu as a representation of this {@link ShopCategory}
     */
    private ItemStack menuItem;

    /**
     * the parent container of this {@link ShopCategory}
     */
    private CategorizedShop<K, V> parent;

    /**
     * The different products with their individual prices
     */
    protected List<ShopElement<K, V>> products = new ArrayList<>();

    /**
     * initializes a new {@link ShopCategory} in the {@link Shop} with a specific name and default menu {@link ItemStack}
     * @param name Name of the shop category which can be used as a unique identifier
     */
    public ShopCategory(String name) {
        this.name = name;
        this.menuItem = PluginUtils.getItem(Material.STONE, name);
    }

    /**
     *
     * initializes a new {@link ShopCategory} in the {@link Shop} with a specific name and menu {@link ItemStack}
     *
     * @param name      Name of the shop category which can be used as a unique identifier
     * @param menuItem  the item used in the menu of the {@link ShopCategory}
     */
    public ShopCategory(String name, ItemStack menuItem) {
        this.name = name;
        this.menuItem = menuItem;
    }

    public ShopCategory<K, V> addProduct(K product, V price, ItemStack itemStack){
        addProduct(new ShopElement<>(product, price, itemStack));
        return this;
    }

    public ShopCategory<K, V> addProduct(ShopElement<K, V> element){
        products.add(element);
        return this;
    }

    public ShopCategory<K, V> addProducts(List<ShopElement<K, V>> elements){
        elements.forEach(this::addProduct);
        return this;
    }

    /**
     *
     * used to set the parent container of the {@link ShopCategory} to allow it to use methods of {@link Shop}
     * such as {@link Shop#buy(ShopElement, Player)} and {@link Shop#getItemStack(ShopElement)}
     *
     * @param parent the shop in which this section is contained
     */
    final void setParent(CategorizedShop<K, V> parent) {
        this.parent = parent;
    }

    public boolean buy(ShopElement<K, V> shopElement, Player player){
        return parent.buy(shopElement, player);
    }

    /**
     * returns the name of the shop, which is used as a unique identifier
     *
     * @return the name of the shop
     */
    public String getName() {
        return name;
    }

    /**
     *
     * returns the item used in the menu of the {@link ShopCategory}
     * @return the used {@link ItemStack}
     */
    public ItemStack getMenuItem() {
        return menuItem;
    }

    /**
     *
     * processes events such as a click event when trying to buy objects
     * @param e the {@link InventoryClickEvent} that has to be processed
     */
    public void handleItemClick(InventoryClickEvent e){
        e.setCancelled(true);
        ItemStack item = e.getCurrentItem();
        if(item==null || item.getType().equals(Material.AIR))return;

        //transforming the clicked item(shopItem) to the actual buyable item
        ShopElement<K, V> itemInShop = parent.getClickedElement(item);
        if(!products.contains(itemInShop))return;

        buy0(itemInShop, (Player) e.getWhoClicked());
    }

    /**
     *
     * internal method to trigger the {@link ShopBuyEvent} before calling the {@link #buy(ShopElement, Player)} method
     *
     * @param shopElement {@link ShopElement} that got bought
     * @param player the player that bought the {@link ShopElement}
     * @return true if player is able to afford product and if the event didn't get cancelled
     */
    boolean buy0(ShopElement<K, V> shopElement, Player player){

        ShopBuyEvent<K, V> buyEvent = new ShopBuyEvent<>(player, shopElement);
        Bukkit.getPluginManager().callEvent(buyEvent);

        if(buyEvent.isCancelled())return false;

        return buy(shopElement, player);
    }


    /**
     * creates an inventory from the shop products which acts as a guy for that shop category
     *
     * @return the created {@link Inventory}
     */
    public Inventory toInventory(){
        Inventory inv = Bukkit.createInventory(null, PluginUtils.convertToMultiplesOfBase(products.size(), 9), name);

        int index = 0;
        for(ShopElement<K, V> product : products){
            inv.setItem(index++, parent.getItemStack(product));
        }
        return inv;
    }

}
