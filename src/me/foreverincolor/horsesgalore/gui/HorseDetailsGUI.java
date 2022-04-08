package me.foreverincolor.horsesgalore.gui;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.foreverincolor.horsesgalore.Main;
import me.foreverincolor.horsesgalore.managers.HorseManager;
import me.foreverincolor.horsesgalore.utils.Utils;

/*
 * GUI that displays details for a specific horse
 */

public class HorseDetailsGUI implements Listener {

	private static Main plugin;
	private static HorseManager horseManager;
	private static ItemStack clickedHorse;
	private int slot;

	public static String inventory_name = Utils.chat("&3Horse Details:");;

	// Constructor
	public HorseDetailsGUI(Main plugin, HorseManager h, ItemStack item, int s) {
		HorseDetailsGUI.plugin = plugin;
		horseManager = h;
		clickedHorse = item;
		slot = s;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	// Creates GUI of player being viewed
	public Inventory viewHorseDetails(Player p) {

		// Get horse info
		ItemMeta meta = clickedHorse.getItemMeta();
		String horseName = meta.getDisplayName();

		// Create basic inventory
		Inventory inv2 = Bukkit.createInventory(null, 36, inventory_name);

		// Finds horse from list
		Horse horse = horseManager.getHorse(p, slot);

		// Get horse stats
		String horseAppearance = "&b" + horse.getColor().toString() + " &b" + horse.getStyle();
		String horseSpeed = "&bSpeed: "
				+ String.format("%.4f", horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue());
		String horseJump = "&bJump Strength: " + String.format("%.4f", horse.getJumpStrength());
		String horseCoords = "&bLocation: " + Math.round(horse.getLocation().getX()) + " "
				+ Math.round(horse.getLocation().getY()) + " " + Math.round(horse.getLocation().getZ());

		// Create items for horse details
		Utils.createInvItem(inv2, 0, "HORSE_SPAWN_EGG", "Horse Name", "&b" + horseName);
		Utils.createInvItem(inv2, 1, "PINK_DYE", "Horse Appearance", horseAppearance);
		Utils.createInvItem(inv2, 2, "SUGAR", "Horse Speed", horseSpeed);
		Utils.createInvItem(inv2, 3, "SPRUCE_FENCE_GATE", "Horse Jump Strength", horseJump);
		Utils.createInvItem(inv2, 4, "COMPASS", "Horse Location", horseCoords);
		Utils.createInvItem(inv2, 7, "SUNFLOWER", "SELL HORSE", "&cWarning: this cannot be undone.");
		Utils.createInvItem(inv2, 8, "BARRIER", "Go Back", "&bClick to view all horses");

		// Sets the inventory
		inv2.setContents(inv2.getContents());
		return inv2;

	}

	// Actions when clicking inventory items
	public static void clicked(Player p, boolean rightClick, int slot, ItemStack clicked, Inventory inv2) {

		// go back to main GUI
		if (clicked.getItemMeta().getDisplayName().equalsIgnoreCase("Go Back")) {
			HorsesGUI inv = new HorsesGUI(plugin, horseManager);
			p.openInventory(inv.viewHorses(p));
		}

		// sell horse
		if (clicked.getItemMeta().getDisplayName().equalsIgnoreCase("SELL HORSE")) {
			p.sendMessage(Utils.chat("&cThis is where you would sell your horse..."));
		}

	}

}
