package me.foreverincolor.horsesgalore.gui;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.foreverincolor.horsesgalore.Main;
import me.foreverincolor.horsesgalore.managers.RaceManager;
import me.foreverincolor.horsesgalore.utils.Utils;

public class MinigameGUI implements Listener {

	private Main plugin;
	public static RaceManager raceManager;

	public static String inventory_name = Utils.chat("&3|| Available Races ||");

	// Constructor
	public MinigameGUI(Main p, RaceManager r) {
		plugin = p;
		raceManager = r;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	// Creates GUI of player being viewed
	public Inventory viewMinigames(Player p) {
		Inventory inv = Bukkit.createInventory(null, 36, inventory_name);

		Set<String> races = raceManager.getAllRaces();
		String raceName, minPlayers, maxPlayers, startPosCount;

		int i = 0;
		for (String r : races) {
			raceName = raceManager.getDisplayName(r);
			minPlayers = "&bMin Players: &3" + raceManager.getMinPlayers(r);
			maxPlayers = "&bMax Players: &3" + raceManager.getMaxPlayers(r);
			if (raceManager.getStartLocations(r) != null) {
			startPosCount = "&bStart Positions Set: &3" + raceManager.getStartLocations(r).size();
			} else { 
				startPosCount = "&bStart Positions Set: &cnone"; 
			}

			// Adds items to inventory
			Utils.createInvItem(inv, i, "SADDLE", raceName, minPlayers, maxPlayers, startPosCount);
			i++;
		}

		// Sets the inventory
		inv.setContents(inv.getContents());
		return inv;
	}

	// Actions when clicking inventory items
	public static void clicked(Player p, boolean rightClick, int slot, ItemStack clicked, Inventory inv) {
		// Do stuff
	}

}
