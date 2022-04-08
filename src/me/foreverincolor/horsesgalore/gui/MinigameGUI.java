package me.foreverincolor.horsesgalore.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.foreverincolor.horsesgalore.Main;
import me.foreverincolor.horsesgalore.files.RaceDataManager;
import me.foreverincolor.horsesgalore.utils.Utils;

public class MinigameGUI implements Listener {

	@SuppressWarnings("unused")
	private static Main plugin;
	@SuppressWarnings("unused")
	private static RaceDataManager file;

	public static String inventory_name;

	// Constructor
	public MinigameGUI(Main plugin, RaceDataManager file) {
		MinigameGUI.plugin = plugin;
		MinigameGUI.file = file;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	// Creates GUI of player being viewed
	public Inventory viewMinigames(Player p) {
		inventory_name = Utils.chat("&3|| Available Races ||");
		Inventory inv = Bukkit.createInventory(null, 36, inventory_name);

		// Sets the inventory
		inv.setContents(inv.getContents());
		return inv;
	}

	// Actions when clicking inventory items
	public static void clicked(Player p, boolean rightClick, int slot, ItemStack clicked, Inventory inv) {

		// Do stuff

	}

}
