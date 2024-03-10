package com.aronck.minigamesapi.elements.shop;

import com.aronck.minigamesapi.events.custom.ShopBuyEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
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
     *
     * creates a new Shop object with a given name and row count
     *
     * @param name the name of the shop
     * @param rows the amount of rows the shop has
     */
    public Shop(String name, int rows) {
        this.name = name;
        this.rows = rows;
        Shops.addShop(name, this);
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
     *
     * @param shopElement {@link ShopElement} that got bought
     * @param player the player that bought the {@link ShopElement}
     * @return true if player is able to afford product
     */
    public abstract boolean buy(ShopElement<K, V> shopElement, Player player);


    public abstract Inventory generateShopInventory();

    /**
     *
     * opens the shop menu
     *
     * @param player the player for which the shop inventory should get opened
     */
    public void openShop(Player player){
        player.openInventory(generateShopInventory());
    }

    /**
     * handles item clicks such as buying items or changing the submenu
     *
     * @param event the {@link InventoryClickEvent}
     */
    public abstract void handleItemClicked(InventoryClickEvent event);

    /**
     *
     * Generates the item representation for the shop element.
     * Takes by default the ItemStacks defined in the ShopElement instance.
     * This method can be overriden to add shop-specific changes to the items e.g. to add the shop name to every Itemstack
     * or to apply the same pattern to every element in the shop
     *
     * @param element the element for which we want to generate the item to show in the shop inventory
     * @return the item that gets shown in the shop inventory
     *
     */
    public ItemStack getItemStack(ShopElement<K, V> element){
        return element.getItemInShopMenu();
    }

    public abstract ShopElement<K, V> getClickedElement(ItemStack itemStack);

}
