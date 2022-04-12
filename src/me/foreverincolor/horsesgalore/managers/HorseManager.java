package me.foreverincolor.horsesgalore.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

import me.foreverincolor.horsesgalore.Main;
import me.foreverincolor.horsesgalore.files.HorseData;

public class HorseManager {

	// Declare any variables here
	private Main plugin;
	private final HorseData horseData;

	// CONSTRUCTOR
	public HorseManager(Main plugin) {
		this.plugin = plugin;
		this.horseData = new HorseData(plugin);
	}

	// METHODS to do stuff

	// Generates file data if not existing, and syncs horse list and count if
	// they're not the same. Should be called before anything where count is used,
	// just to be safe.
	private void checkFile(OfflinePlayer p) {
		List<String> horseList = getHorseList(p);
		int count = horseList.size();

		// sets count to be list size
		horseData.getConfig().set("players." + p.getUniqueId().toString() + ".horse-count", (count));
		horseData.saveConfig();
	}

	// Returns count of horses, syncs data beforehand
	public int horseCount(OfflinePlayer p) {
		// ensures player has data, and it is correct
		checkFile(p);
		int count = horseData.getConfig().getInt("players." + p.getUniqueId().toString() + ".horse-count");
		return count;
	}

	// Gets the list of horses for the player
	public List<String> getHorseList(OfflinePlayer p) {
		List<String> horseList = new ArrayList<String>();
		horseList = horseData.getConfig().getStringList("players." + p.getUniqueId().toString() + ".horse-list");
		return horseList;
	}

	// Gets a specific horse if the slot is known
	public Horse getHorse(OfflinePlayer p, int slot) {
		List<String> horseList = new ArrayList<String>();
		horseList = getHorseList(p);
		UUID horseUUID = UUID.fromString(horseList.get(slot));
		Horse horse = (Horse) Bukkit.getEntity(horseUUID);
		return horse;
	}

	// Adds horse to the file
	public boolean addHorse(OfflinePlayer p, Horse h) {
		// check count & max, make sure not over max
		int count = horseCount(p);

		if (count >= plugin.getConfig().getInt("taming.limit")) {
			return false;
		}

		// increase horse count
		horseData.getConfig().set("players." + p.getUniqueId().toString() + ".horse-count", (count + 1));
		horseData.saveConfig();

		// add horse id to list
		List<String> horseList = new ArrayList<String>();
		horseList = horseData.getConfig().getStringList("players." + p.getUniqueId().toString() + ".horse-list");

		horseList.add(h.getUniqueId().toString());
		horseData.getConfig().set("players." + p.getUniqueId().toString() + ".horse-list", horseList);
		horseData.saveConfig();
		return true;
	}

	// Removes horse from file
	public void removeHorse(Player p, Horse h) {
		// decrease count
		int count = horseCount(p);
		horseData.getConfig().set("players." + p.getUniqueId().toString() + ".horse-count", (count - 1));
		horseData.saveConfig();

		// remove horse id from file list
		List<String> horseList = new ArrayList<String>();
		horseList = horseData.getConfig().getStringList("players." + p.getUniqueId().toString() + ".horse-list");

		int horseIndex = horseList.indexOf(h.getUniqueId().toString());
		horseList.remove(horseIndex);

		horseData.getConfig().set("players." + p.getUniqueId().toString() + ".horse-list", horseList);
		horseData.saveConfig();
	}
}
