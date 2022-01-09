package com.aronck.minigamesapi.elements.shop;

import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;

/**
 *
 * The class that manages the shops and its actions such as the distribution of events
 * and allows the user to search for specific shops
 */
public final class Shops {

    private Shops(){}

    /**
     * hashMap to allow the search by name to be O(1)
     */
    private static HashMap<String, Shop<?, ?>> shops = new HashMap<>();

    /**
     *
     * registers a new shop
     *
     * @param shopName
     * @param shop
     */
    public static void addShop(String shopName, Shop<?, ?> shop){
        shops.put(shopName, shop);
    }

    /**
     *
     *searches for a Shop with the given name
     * @param name
     * @return the shop with the given name
     */
    public static Shop<?, ?> getShopByName(String name){
        return shops.get(name);
    }

    /**
     *
     * processes an {@link InventoryClickEvent} and distributes it to the
     * {@link ShopCategory#handleItemClick(InventoryClickEvent)} and the
     * {@link Shop#handleItemClicked(InventoryClickEvent)} method
     *
     * @param e the {@link InventoryClickEvent} that gets processed
     */
    public static void processClickEvent(InventoryClickEvent e) {
        if(e.getClickedInventory()==null)return;
        String title = e.getView().getTitle();
        if(shops.get(title)!=null){
            shops.get(title).handleItemClicked(e);
        }else{
            //find any category with the same name as the clicked inventory
            shops.values()
                    .stream()
                    .filter(shop -> shop instanceof CategorizedShop) //take only the categorized shops
                    .map(shop -> (CategorizedShop<?, ?>) shop) //cast the shops to categorized shops
                    .flatMap(categorizedShop -> categorizedShop.getCategories().stream())
                    .filter(o -> title.equals(o.getName())) //check if the name is the same as the clickedInventory title
                    .findFirst()
                    .ifPresent(o -> o.handleItemClick(e));
        }
    }
}
