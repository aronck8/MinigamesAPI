package com.aronck.minigamesapi.elements.shop;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class CategorizedShop<K, V> extends Shop<K, V>{

    private List<ShopCategory<K, V>> categories = new ArrayList<>();

    private boolean canContainCategoryTwice = false;

    public CategorizedShop(String name, int rows) {
        super(name, rows);
    }

    public CategorizedShop(String name, int rows, boolean canContainCategoryTwice) {
        super(name, rows);
        this.canContainCategoryTwice = canContainCategoryTwice;
    }

    public void addCategory(ShopCategory<K, V> shopCategory){
        shopCategory.setParent(this);
        if(!canContainCategoryTwice && categories.contains(shopCategory))return;
        categories.add(shopCategory);
    }

    public List<ShopCategory<K, V>> getCategories(){
        return categories;
    }

    public Inventory generateShopMenu(){
        Inventory inv = Bukkit.createInventory(null, rows*9, name);
        if(categories.size()>=rows*9)throw new ArrayIndexOutOfBoundsException("There are more categories than free rows!");

        for (int i = 0; i < categories.size(); i++) {
            ShopCategory<K, V> shopCategory = categories.get(i);
            inv.setItem(i, shopCategory.getMenuItem());
        }

        return inv;
    }

    @Override
    public void openShop(Player player) {
        player.openInventory(generateShopMenu());
    }

    @Override
    public void handleItemClicked(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        for(ShopCategory<K, V> category : categories){
            if(category.getMenuItem().equals(item)){
                e.setCancelled(true);
                e.getWhoClicked().openInventory(category.toInventory());
            }
        }
    }
}
