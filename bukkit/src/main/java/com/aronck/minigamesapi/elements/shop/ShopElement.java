package com.aronck.minigamesapi.elements.shop;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ShopElement<K, V> {

    private List<K> offeredElements = new ArrayList<>();
    private List<V> requiredElements = new ArrayList<>();
    private ItemStack itemInShopMenu = null;

    public ShopElement(ItemStack itemInShopMenu){
        this.itemInShopMenu = itemInShopMenu;
    }
    public ShopElement(K product, V price, ItemStack itemInShopMenu){
        offeredElements.add(product);
        requiredElements.add(price);
        this.itemInShopMenu = itemInShopMenu;
    }

    public ShopElement<K, V> addOfferedElement(K product){
        offeredElements.add(product);
        return this;
    }

    public ShopElement<K, V> addRequiredElement(V price){
        requiredElements.add(price);
        return this;
    }

    public List<K> getOfferedElements() {
        return offeredElements;
    }

    public List<V> getRequiredElements() {
        return requiredElements;
    }

    public ItemStack getItemInShopMenu() {
        return itemInShopMenu;
    }
}
