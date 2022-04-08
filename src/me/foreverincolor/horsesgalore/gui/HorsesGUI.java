package me.foreverincolor.horsesgalore.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.foreverincolor.horsesgalore.Main;
import me.foreverincolor.horsesgalore.managers.HorseManager;
import me.foreverincolor.horsesgalore.utils.Utils;

/*
 * GUI that displays all of a player's horses
 */

public class HorsesGUI implements Listener {

	private static Main plugin;
	private static HorseManager horseManager;

	public static String inventory_name = Utils.chat("&3|| Your Horses ||");

	// Constructor
	public HorsesGUI(Main plugin, HorseManager h) {
		HorsesGUI.plugin = plugin;
		horseManager = h;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	// Creates GUI of player being viewed
	public Inventory viewHorses(Player p) {
		Inventory inv = Bukkit.createInventory(null, 36, inventory_name);
		String horseName;
		Horse horse;

		// Gets the list of horses
		List<String> horseList = new ArrayList<String>();
		horseList = horseManager.getHorseList(p);

		// Creates inventory objects
		for (int i = 0; i < horseList.size(); i++) {
			UUID horseUUID = UUID.fromString(horseList.get(i));
			horse = (Horse) Bukkit.getEntity(horseUUID);

			// in case there is a problem with tame list
			if (horse == null) {
				Bukkit.broadcastMessage("REMOVE HORSE FROM LIST: " + horseUUID);
				horseList.remove(i);
			}

			if (horse.getCustomName() == null) {
				horseName = "Unnamed Horse";
			} else {
				horseName = horse.getCustomName();
			}

			// Creates egg for horse
			Utils.createInvItem(inv, i, "HORSE_SPAWN_EGG", "&f&l" + horseName, "&7click to view details");
		}

		// Sets the inventory
		inv.setContents(inv.getContents());
		return inv;
	}

	// Actions when clicking inventory items
	public static void clicked(Player p, boolean rightClick, int slot, ItemStack clicked, Inventory inv) {

		// Open new GUI for specific horse
		HorseDetailsGUI inv2 = new HorseDetailsGUI(plugin, horseManager, clicked, slot);
		p.openInventory(inv2.viewHorseDetails(p));

	}

}
