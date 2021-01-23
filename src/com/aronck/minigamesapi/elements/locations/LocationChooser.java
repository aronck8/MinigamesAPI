package com.aronck.minigamesapi.elements.locations;

import com.aronck.minigamesapi.utils.PluginUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LocationChooser {

	private String key;
	private ChooserType type;
	private ItemStack item;

	public LocationChooser(String key, ChooserType type) {
		this.key = key;
		this.type = type;
		item = PluginUtils.getItem(Material.STONE, key);
	}

	public LocationChooser(String key, ChooserType type, ItemStack item) {
		this.key = key;
		this.type = type;
		this.item = item;
	}

	public String getKey() {
		return key;
	}

	public ChooserType getType() {
		return type;
	}

	public ItemStack getItem(){
		return item;
	}

	@Override
	public String toString() {
		return "Key: " + key + "; type: " + type;
	}
}


