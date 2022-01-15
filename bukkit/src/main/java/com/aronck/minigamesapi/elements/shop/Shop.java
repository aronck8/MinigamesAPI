package com.aronck.minigamesapi.elements.shop;

import com.aronck.minigamesapi.events.custom.ShopBuyEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * The shop class serves as the superclass for all minigame shops such as the {@link CategorizedShop}
 * Every shop has to have a unique name and a given size
 *
 *
 *
 * @param <K> the type of the buyable objects
 * @param <V> the type of the currency e.g Integer for money or any object as a kind of "exchange"
 */
public abstract class Shop<K, V> {

    protected String name;

    protected int rows;

    /**
     * The different products with their individual prices
     */
    protected List<ShopElement<K, V>> products = new ArrayList<>();

    /**
     * HashMap used to create a link between a buyable and its symbol in the menu
     */
    protected HashMap<ItemStack, ShopElement<K, V>> shopSymbolsForObject = new HashMap<>();

    /**
     *
     * creates a new Shop object with a given name and row count
     *
     * @param name
     * @param rows
     */
    public Shop(String name, int rows) {
        this.name = name;
        this.rows = rows;
        Shops.addShop(name, this);
    }

    public Shop<K, V> addProduct(K product, V price, ItemStack itemStack){
        addProduct(new ShopElement<>(product, price, itemStack));
        return this;
    }

    public Shop<K, V> addProduct(ShopElement<K, V> element){
        products.add(element);
        shopSymbolsForObject.put(element.getItemInShopMenu(), element);
        return this;
    }

    /**
     *
     * internal method to trigger the {@link ShopBuyEvent} before calling the {@link #buy(ShopElement, Player)} method
     *
     * @param shopElement
     * @param player
     * @return true if player is able to afford product and if the event didn't get cancelled
     */
    boolean buy0(ShopElement<K, V> shopElement, Player player){

        ShopBuyEvent<K, V> buyEvent = new ShopBuyEvent<>(player, shopElement);
        Bukkit.getPluginManager().callEvent(buyEvent);

        if(buyEvent.isCancelled())return false;

        return buy(shopElement, player);
    }

    /**
     *
     * @param shopElement
     * @param player
     * @return true if player is able to afford product
     */
    public abstract boolean buy(ShopElement<K, V> shopElement, Player player);

    /**
     *
     * opens the shop menu
     *
     * @param player
     */
    public abstract void openShop(Player player);

    /**
     * handles item clicks such as buying items or changing the submenu
     *
     * @param event the {@link InventoryClickEvent}
     */
    public abstract void handleItemClicked(InventoryClickEvent event);

    public abstract ItemStack getItemStack(K key, V value);

}
