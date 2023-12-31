package com.aronck.minigamesapi.utils;

import com.aronck.minigamesapi.minigame.Minigame;
import com.aronck.minigamesapi.elements.locations.ChooserType;
import com.aronck.minigamesapi.elements.locations.LocationChooser;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class PluginUtils {

	private PluginUtils(){}

	public static void openLocationChooserInventory(Minigame minigame, Player player){

		if(!player.hasPermission("minigames.setup")) return;
		List<LocationChooser> choosers = minigame.getLocationChoosers().stream()
				.filter(locationChooser -> locationChooser.getType().equals(ChooserType.GUI))
				.collect(Collectors.toList());

		Inventory inv = Bukkit.createInventory(player, convertToMultiplesOfBase(choosers.size(), 9), PluginConstants.LOCATION_CHOOSER_INVENTORY_NAME);

		for (int i = 0; i < choosers.size(); i++) {
			LocationChooser chooser = choosers.get(i);
			inv.setItem(i, chooser.getItem());
		}

		player.openInventory(inv);

	}

	public static int convertToMultiplesOfBase(int number, int base) {
		if (number % base == 0) return number;
		else return (number + (base - (number % base)));

	}

	public static int getRandomInt(int min, int max){
		return (int)(Math.random()*(max-min+1))+min;
	}

	public static int getRandomInt(int max){
		return getRandomInt(0, max);
	}

	public static <T> T getRandomObjectFromList(List<T> list){
		return list.get(getRandomInt(list.size()-1));
	}

	public static ItemStack getItem(Material material, String name){
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getItem(Material material, int amount, String name){
		ItemStack item = new ItemStack(material, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getItem(Material material, int amount, String name, String... lore){
		ItemStack item = new ItemStack(material, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getItem(Material material, int amount, String name, Enchantment enchantment, int level, boolean b){
		ItemStack item = new ItemStack(material, amount);
		ItemMeta meta = item.getItemMeta();
		meta.addEnchant(enchantment, level, b);
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getItem(ItemStack itemStack, String name){
		ItemStack item = new ItemStack(itemStack);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getItem(ItemStack itemStack, int amount, String name){
		ItemStack item = new ItemStack(itemStack);
		item.setAmount(amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getItem(ItemStack itemStack, int amount, String name, String... lore){
		ItemStack item = new ItemStack(itemStack);
		item.setAmount(amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getDyedWool(DyeColor color, int amount, String name, String... lore){
		ItemStack item = new Wool(color).toItemStack();
		return getItem(item, amount, name, lore);
	}

}
