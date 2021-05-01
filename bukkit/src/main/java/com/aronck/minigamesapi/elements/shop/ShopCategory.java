package com.aronck.minigamesapi.elements.shop;

import com.aronck.minigamesapi.utils.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 *
 *
 * @param <K>t he type of the buyable objects
 * @param <V> the type of the currency e.g Integer for money or any object as a kind of "exchange"
 *
 */
public class ShopCategory<K, V> {

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
    private HashMap<K, V> priceOfProduct = new HashMap<>();

    /**
     * HashMap used to create a link between a buyable and its symbol in the menu
     */
    private HashMap<ItemStack, K> shopSymbolsForObject = new HashMap<>();

    /**
     * initializes a new {@link ShopCategory} in the {@link Shop} with a specific name and default menu {@link ItemStack}
     * @param name Name of the shop category which can be used as a unique identifier
     */
    public ShopCategory(CategorizedShop<K, V> parent, String name) {
        this.parent = parent;
        parent.addCategory(this);
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
    public ShopCategory(CategorizedShop<K, V> parent, String name, ItemStack menuItem) {
        this.parent = parent;
        parent.addCategory(this);
        this.name = name;
        this.menuItem = menuItem;
    }

    /**
     *
     * initializes a new {@link ShopCategory} in the {@link Shop} with a specific name, menu {@link ItemStack}, and a list of products and their prices
     *
     * @param name      Name of the shop category which can be used as a unique identifier
     * @param menuItem  the item used in the menu of the {@link ShopCategory}
     * @param products  the list of available products in the {@link ShopCategory}
     * @param prizes    the list of the prices of the products
     */
    public ShopCategory(CategorizedShop<K, V> parent, String name, ItemStack menuItem, K[] products, V[] prizes){
        this.parent = parent;
        parent.addCategory(this);
        this.name = name;
        this.menuItem = menuItem;
        for(int i = 0;i<products.length;i++){
            if(prizes.length>i) priceOfProduct.put(products[i], prizes[i]);
            else priceOfProduct.put(products[i], null);
        }
    }

    /**
     *
     * used to set the parent container of the {@link ShopCategory} to allow it to use methods of {@link Shop}
     * such as {@link Shop#buy(Object, Object, Player)} and {@link Shop#getItemStack(Object, Object)}
     *
     * @param parent the shop in which this section is contained
     */
    @Deprecated
    final void setParent(CategorizedShop<K, V> parent) {
        this.parent = parent;
    }

    /**
     *
     * adds a new buyable item to this {@link ShopCategory}
     *
     * @param product       product you want to add
     * @param price         price the user has to give away in exchange of the product
     * @return
     */
    public ShopCategory<K, V> addProduct(K product, V price){
        priceOfProduct.put(product, price);
        shopSymbolsForObject.put(parent.getItemStack(product, price), product);
        return this;
    }

    /**
     *
     * returns the object one has to pay for a given object in the shop
     *
     * @param product the product from which we want to now the price
     * @return the specific price of a product
     */
    public Object getPrizeOfProduct(K product){
        return priceOfProduct.get(product);
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

        ItemStack item = e.getCurrentItem();
        if(item==null || item.getType()==null || Material.AIR.equals(item.getType()))return;

        //transforming the clicked item(shopItem) to the actual buyable item
        K itemInShop = shopSymbolsForObject.get(item);
        if(!priceOfProduct.containsKey(itemInShop))return;

        parent.buy0(itemInShop, priceOfProduct.get(itemInShop), (Player) e.getWhoClicked());
        e.setCancelled(true);
    }


    /**
     * creates an inventory from the shop products which acts as a guy for that shop category
     *
     * @return the created {@link Inventory}
     */
    public Inventory toInventory(){
        Inventory inv = Bukkit.createInventory(null, PluginUtils.convertToMultiplesOfBase(priceOfProduct.size(), 9), name);

        int index = 0;
        for(K key : priceOfProduct.keySet()){
            inv.setItem(index++, parent.getItemStack(key, priceOfProduct.get(key)));
        }

        return inv;

    }

}
