package com.aronck.minigamesapi.elements.teams.kit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class Kit {

    private ItemStack[] contents;
    private ItemStack[] armorContents;

    public static final Kit EMPTY_KIT = new Kit();

    public Kit(ItemStack... contents){
        this.contents = contents;
    }

    public Kit(ItemStack[] contents, ItemStack[] armorContents) {
        this.contents = contents;
        this.armorContents = armorContents;
    }

    public ItemStack[] getContents() {
        return contents;
    }

    public List<ItemStack> getContentsList(){
        return Arrays.asList(contents);
    }

    public ItemStack[] getArmorContents() {
        return armorContents;
    }

    public List<ItemStack> getArmourContentsList(){
        return Arrays.asList(armorContents);
    }

    public void applyToPlayer(Player player){
        player.getInventory().setArmorContents(armorContents);
        player.getInventory().setContents(contents);
        player.updateInventory();
    }

}
