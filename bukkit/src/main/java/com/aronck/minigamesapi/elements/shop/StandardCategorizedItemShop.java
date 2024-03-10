package com.aronck.minigamesapi.elements.shop;

import com.aronck.minigamesapi.utils.PluginUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


public class StandardCategorizedItemShop extends CategorizedShop<ItemStack, ItemStack>{

    /**
     * HashMap used to create a link between a buyable and its symbol in the menu
     */
    protected HashMap<ItemStack, ShopElement<ItemStack, ItemStack>> shopSymbolsForObject = new HashMap<>();

    public StandardCategorizedItemShop(String name, int rows) {
        super(name, rows);
    }

    @Override
    public boolean buy(ShopElement<ItemStack, ItemStack> shopElement, Player player) {

        Set<Map.Entry<ItemStack, Integer>> requiredElements = shopElement.getRequiredElements().entrySet();

        HashMap<ItemStack, Integer> requiredElementsMap = new HashMap<>();

        for(Map.Entry<ItemStack, Integer> requiredElement : requiredElements){
            //set the amount to 1 to have the Itemstacks that function as keys to be able to use
            //the Itemstack#equals method instead of the ItemStack#isSimilar method to search for the item in the hashmap independently from the different amounts
            //(because otherwise I couldn't use the default HashMap methods like HashMap.contains
            ItemStack normalizedItemStack = requiredElement.getKey().clone();
            normalizedItemStack.setAmount(1);

            if(!requiredElementsMap.containsKey(requiredElement.getKey())){
                requiredElementsMap.put(normalizedItemStack, requiredElement.getKey().getAmount()*requiredElement.getValue());
            }else{
                requiredElementsMap.put(normalizedItemStack, requiredElementsMap.get(normalizedItemStack)
                        + requiredElement.getKey().getAmount()*requiredElement.getValue());
            }
        }

        for(Map.Entry<ItemStack, Integer> requiredElement : requiredElementsMap.entrySet()){
            if(!player.getInventory().containsAtLeast(requiredElement.getKey(), requiredElement.getValue())){
                player.sendMessage("§cNicht genügend Items!");
                return false;
            }
        }

        for(Map.Entry<ItemStack, Integer> requiredElement : requiredElementsMap.entrySet()){
            for(int i = 0;i<requiredElement.getValue();i++){
                player.getInventory().removeItem(requiredElement.getKey());
            }
        }

        for(Map.Entry<ItemStack, Integer> offeredElement : shopElement.getOfferedElements().entrySet()){
            for(int i = 0;i<offeredElement.getValue();i++) {
                player.getInventory().addItem(offeredElement.getKey());
            }
        }

        player.sendMessage("§aDu hast das Item erfolgreich gekauft!");
        return true;
    }

    private String getItemNameFromMaterial(Material material){
        return material.name().replaceAll("_", " ").toLowerCase();
    }

    @Override
    public ShopElement<ItemStack, ItemStack> getClickedElement(ItemStack itemStack) {
        return shopSymbolsForObject.get(itemStack);
    }


    @Override
    public ItemStack getItemStack(ShopElement<ItemStack, ItemStack> element) {

        ItemStack suggestedItem = element.getItemInShopMenu();
        ItemStack item = PluginUtils.getItem(suggestedItem, suggestedItem.getAmount(), generateItemName(element), generateLore(element));

        shopSymbolsForObject.put(item, element);
        return item;
    }

    protected String[] generateLore(ShopElement<ItemStack, ItemStack> element){
        String[] lore = new String[element.getOfferedElements().size()+element.getRequiredElements().size()+3];

        lore[0] = "§bKaufe:";
        int arrayIndex = 1;
        for(Map.Entry<ItemStack, Integer> entry : element.getOfferedElements().entrySet()){
            lore[arrayIndex++]=entry.getValue() + " " + (entry.getKey().getItemMeta()==null || entry.getKey().getItemMeta().getDisplayName().isEmpty()
                    ? getItemNameFromMaterial(entry.getKey().getType())
                    : entry.getKey().getItemMeta().getDisplayName());
        }

        if(element.getRequiredElements().size()==0)return lore;

        lore[arrayIndex++]="";
        lore[arrayIndex++]="§bfür";

        for(Map.Entry<ItemStack, Integer> entry : element.getRequiredElements().entrySet()){
            lore[arrayIndex++]=entry.getValue() + " " + (entry.getKey().getItemMeta()==null || entry.getKey().getItemMeta().getDisplayName().isEmpty()
                    ? getItemNameFromMaterial(entry.getKey().getType())
                    : entry.getKey().getItemMeta().getDisplayName());
        }
        return lore;
    }

    protected String generateItemName(ShopElement<ItemStack, ItemStack> element){
        ItemStack suggestedItem = element.getItemInShopMenu();

        //try searching for the pre-defined itemname
        if(suggestedItem.getItemMeta()!=null && !suggestedItem.getItemMeta().getDisplayName().isEmpty())return suggestedItem.getItemMeta().getDisplayName();

        //if not successful continue searching for a usable/defined name in the offered and required elements

        Optional<Map.Entry<ItemStack, Integer>> anyOfferedItemOptional = element.getOfferedElements().entrySet().stream().findAny();
        if(anyOfferedItemOptional.isPresent()){
            ItemStack item = anyOfferedItemOptional.get().getKey();
            if(item.getItemMeta()!=null && !item.getItemMeta().getDisplayName().isEmpty())return item.getItemMeta().getDisplayName();
        }

        Optional<Map.Entry<ItemStack, Integer>> anyRequiredItemOptional = element.getRequiredElements().entrySet().stream().findAny();
        if(anyRequiredItemOptional.isPresent()){
            ItemStack item = anyRequiredItemOptional.get().getKey();
            if(item.getItemMeta()!=null && !item.getItemMeta().getDisplayName().isEmpty())return "Zahle: " + item.getItemMeta().getDisplayName();
        }


        //if no names could be found try using the item names as Displaynames
        if(anyOfferedItemOptional.isPresent()){
            return getItemNameFromMaterial(anyOfferedItemOptional.get().getKey().getType());
        }

        if(anyRequiredItemOptional.isPresent()){
            return getItemNameFromMaterial(anyRequiredItemOptional.get().getKey().getType());
        }

        return "§cKein Produkt verfügbar!";
    }


    

}
