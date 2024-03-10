package com.aronck.minigamesapi.elements.shop;

import com.aronck.minigamesapi.utils.Tuple;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopElement<K, V> {

    private Map<K, Integer> offeredElements = new HashMap<>();
    private Map<V, Integer> requiredElements = new HashMap<>();
    private ItemStack itemInShopMenu;

    public ShopElement(ItemStack itemInShopMenu){
        this.itemInShopMenu = itemInShopMenu;
    }
    public ShopElement(K product, V price, ItemStack itemInShopMenu){
        addOfferedElement(product);
        addRequiredElement(price);
        this.itemInShopMenu = itemInShopMenu;
    }

    public ShopElement<K, V> addOfferedElement(K product){
        addOfferedElement(product, 1);
        return this;
    }

    public ShopElement<K, V> addOfferedElement(K product, int amount){
        Integer currentAmount;
        if((currentAmount = offeredElements.get(product))!=null){
            offeredElements.put(product, currentAmount+amount);
        }else{
            offeredElements.put(product, amount);
        }
        return this;
    }

    public ShopElement<K, V> addRequiredElement(V price){
        addRequiredElement(price, 1);
        return this;
    }

    public ShopElement<K, V> addRequiredElement(V price, int amount){
        Integer currentAmount;
        if((currentAmount = requiredElements.get(price))!=null){
            requiredElements.put(price, currentAmount+amount);
        }else{
            requiredElements.put(price, amount);
        }
        return this;
    }

    public Map<K, Integer> getOfferedElements() {
        return offeredElements;
    }

    public Map<V, Integer> getRequiredElements() {
        return requiredElements;
    }

    public ItemStack getItemInShopMenu() {
        return itemInShopMenu;
    }
}
