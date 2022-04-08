package me.foreverincolor.horsesgalore.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils {

	// Chat Color Code
	public static String chat(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	// Create Inventory Item 
	public static ItemStack createInvItem(Inventory inv, int slot, String element, String title, String... loreString) {
		ItemStack item;
		List<String> lore = new ArrayList<String>();

		item = new ItemStack(Material.getMaterial(element), 1);

		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Utils.chat(title));

		for (String s : loreString) {
			lore.add(Utils.chat(s));
		}

		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setLore(lore);
		item.setItemMeta(meta);

		inv.setItem(slot, item);

		return item;
	}
	
	public static ItemStack createItem(String element, String... loreString) {
		ItemStack item;
		List<String> lore = new ArrayList<String>();

		item = new ItemStack(Material.getMaterial(element), 1);	
		ItemMeta meta = item.getItemMeta();

		for (String s : loreString) {
			lore.add(Utils.chat(s));
		}

		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item; 
	
	}
	
	public static ItemStack createNamedItem(String element, String title, String... loreString) {
		ItemStack item;
		List<String> lore = new ArrayList<String>();

		item = new ItemStack(Material.getMaterial(element), 1);	
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Utils.chat(title));

		for (String s : loreString) {
			lore.add(Utils.chat(s));
		}

		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item; 
	
	}
	
	// Get Location from string
	public static Location getLoc(String locString) {
		if (locString == null || locString.trim() == "") {
			return null;
		}

		String[] locArray = locString.split(" ");

		if (locArray.length == 4) {
			World w = Bukkit.getServer().getWorld(locArray[0].trim());
			int x = Integer.parseInt(locArray[1]);
			int y = Integer.parseInt(locArray[2]);
			int z = Integer.parseInt(locArray[3]);

			Location loc = new Location(w, x, y, z);
			return loc;
		}
		return null;
	}


}
