package com.aronck.minigamesapi.elements.shop;

import com.aronck.minigamesapi.utils.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public abstract class CategorizedShop<K, V> extends Shop<K, V>{


    private ArrayList<ShopCategory<K, V>> categories = new ArrayList<>();

    private boolean canContainCategoryTwice = false;

    public CategorizedShop(String name, int rows) {
        super(name, rows);
        initCategoriesArrayList(rows*9);
    }

    public CategorizedShop(String name, int rows, boolean canContainCategoryTwice) {
        super(name, rows);
        this.canContainCategoryTwice = canContainCategoryTwice;
        initCategoriesArrayList(rows*9);
    }

    private void initCategoriesArrayList(int elements){
        categories.clear();
        for (int i = 0;i<elements;i++){
            categories.add(i, null);
        }
    }

    public void addCategory(ShopCategory<K, V> shopCategory){
        shopCategory.setParent(this);
        if(!canContainCategoryTwice && categories.contains(shopCategory))throw new UnsupportedOperationException("The shop is configured to only allow a given category once");

        int freeSlot = categories.indexOf(null);
        if(freeSlot==-1)throw new ArrayStoreException("Max number of categories reached");
        categories.set(freeSlot, shopCategory);
    }

    public void addCategory(ShopCategory<K, V> shopCategory, int index){

        shopCategory.setParent(this);
        if(!canContainCategoryTwice && categories.contains(shopCategory))throw new UnsupportedOperationException("The shop is configured to only allow a given category once");

        int freeSlot = categories.indexOf(null);
        if(freeSlot==-1)throw new ArrayStoreException("Max number of categories reached");

        if(categories.get(index)==null){
            categories.set(index, shopCategory);
            return;
        }


        categories = PluginUtils.createFreeSpaceInArrayList(categories, index);
        categories.set(index, shopCategory);
    }

    public List<ShopCategory<K, V>> getCategories(){
        return categories;
    }


    public Collection<ShopCategory<K, V>> getImmutableCategoriesList(){
        return Collections.unmodifiableCollection(categories);
    }


    @Override
    public Inventory generateShopInventory(){
        Inventory inv = Bukkit.createInventory(null, rows*9, name);
        if(categories.size()>(rows*9))throw new ArrayIndexOutOfBoundsException("There are more categories than free rows!");

        for (int i = 0; i < categories.size(); i++) {
            ShopCategory<K, V> shopCategory = categories.get(i);
            if (shopCategory==null)continue;
            inv.setItem(i, shopCategory.getMenuItem());
        }

        return inv;
    }

    @Override
    public void handleItemClicked(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        for(ShopCategory<K, V> category : categories){
            if(category==null)continue;
            if(category.getMenuItem().equals(item)){
                e.setCancelled(true);
                e.getWhoClicked().openInventory(category.toInventory());
            }
        }
    }
}
