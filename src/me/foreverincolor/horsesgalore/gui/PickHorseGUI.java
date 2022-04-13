package me.foreverincolor.horsesgalore.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.foreverincolor.horsesgalore.Main;
import me.foreverincolor.horsesgalore.managers.GameManager;
import me.foreverincolor.horsesgalore.managers.HorseManager;
import me.foreverincolor.horsesgalore.utils.Utils;

public class PickHorseGUI implements Listener {

	private Main plugin;
	public static GameManager gameManager;
	public static HorseManager horseManager;

	public static String inventory_name = Utils.chat("&3Pick your race horse:");

	// Constructor
	public PickHorseGUI(Main p, GameManager g, HorseManager h) {
		plugin = p;
		gameManager = g;
		horseManager = h;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	// Creates GUI of player being viewed
	public Inventory viewRaceHorses(Player p) {
		Inventory inv = Bukkit.createInventory(null, 36, inventory_name);
		String horseName;
		String horseCoords;
		String horseSpeed;
		String horseJump;
		String horseAppearance;

		List<String> horseList = new ArrayList<String>();
		horseList = horseManager.getHorseList(p);

		// Creates inventory objects
		for (int i = 0; i < horseList.size(); i++) {
			UUID horseUUID = UUID.fromString(horseList.get(i));
			Horse horse = (Horse) Bukkit.getEntity(horseUUID);

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

			// Display stats
			horseAppearance = "&b" + horse.getColor().toString() + " &b" + horse.getStyle();
			horseSpeed = "&aSpeed: "
					+ String.format("%.4f", horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue());
			horseJump = "&aJump Strength: " + String.format("%.4f", horse.getJumpStrength());
			horseCoords = "&aLocation: " + Math.round(horse.getLocation().getX()) + " "
					+ Math.round(horse.getLocation().getY()) + " " + Math.round(horse.getLocation().getZ());

			Utils.createInvItem(inv, i, "HORSE_SPAWN_EGG", horseName, horseAppearance, horseSpeed, horseJump,
					horseCoords);
		}

		// Sets the inventory
		inv.setContents(inv.getContents());
		return inv;
	}

	// Actions when clicking inventory items
	public static void clicked(Player p, boolean rightClick, int slot, ItemStack clicked, Inventory inv) {

		// Finds horse that was clicked
		Horse horse = horseManager.getHorse(p, slot);
		
		// Checks if horse has a saddle
		if (horse.getInventory().getSaddle() != null) { 
			// Adds horse to game
			gameManager.addHorse(p, horse);
			p.closeInventory();
		} else { 
			p.closeInventory(); 
			p.sendMessage(Utils.chat("&cYour horse is not wearing a saddle! Please put one on to be able to race!"));
		}
		


	}

}
