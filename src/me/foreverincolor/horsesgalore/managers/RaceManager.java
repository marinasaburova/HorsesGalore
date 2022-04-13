package me.foreverincolor.horsesgalore.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;

import me.foreverincolor.horsesgalore.Main;
import me.foreverincolor.horsesgalore.files.RaceData;

public class RaceManager {

	@SuppressWarnings("unused")
	private Main plugin;
	private final RaceData raceData;

	// CONSTRUCTOR
	public RaceManager(Main plugin) {
		this.plugin = plugin;
		this.raceData = new RaceData(plugin);
	}

	// METHODS
	public void createRace(String raceName) {
		raceData.getConfig().set("races." + raceName, raceName);
		raceData.saveConfig();

		setMinPlayers(raceName, 2);
		setMaxPlayers(raceName, 8);
	}

	/*
	 * SETTERS
	 */

	public void setDisplayName(String raceName, String displayName) {
		raceData.getConfig().set("races." + raceName + ".display-name", displayName);
		raceData.saveConfig();

	}

	public void setMinPlayers(String raceName, int minPlayers) {
		raceData.getConfig().set("races." + raceName + ".min-players", minPlayers);
		raceData.saveConfig();

	}

	public void setMaxPlayers(String raceName, int maxPlayers) {
		raceData.getConfig().set("races." + raceName + ".max-players", maxPlayers);
		raceData.saveConfig();

	}

	public void addStart(String raceName, String loc) {
		List<String> startList = new ArrayList<String>();
		startList = raceData.getConfig().getStringList("races." + raceName + ".start-locations");
		startList.add(loc);
		raceData.getConfig().set("races." + raceName + ".start-locations", startList);
		raceData.saveConfig();

	}

	public boolean removeStart(String raceName, int num) {
		List<String> startList = new ArrayList<String>();
		startList = raceData.getConfig().getStringList("races." + raceName + ".start-locations");
		boolean success = false; 
		try {
			startList.remove(num);
			raceData.getConfig().set("races." + raceName + ".start-locations", startList);
			raceData.saveConfig();
			success = true; 
		} catch (ArrayIndexOutOfBoundsException e) {
			success = false; 
		}
		
		return success;

	}

	public void setFinish(String raceName, Location loc) {
		;
		raceData.getConfig().set("races." + raceName + ".finish-location", loc.toString());
		raceData.saveConfig();

	}

	/*
	 * GETTERS
	 */

	public String getDisplayName(String raceName) {
		String displayName = raceName;

		if (raceData.getConfig().contains("races." + raceName + ".display-name")) {
			displayName = raceData.getConfig().getString("races." + raceName + ".display-name");
		}

		return displayName;

	}

	public int getMinPlayers(String raceName) {
		int minPlayers = 2;

		if (raceData.getConfig().contains("races." + raceName + ".min-players")) {
			minPlayers = raceData.getConfig().getInt("races." + raceName + ".min-players");
		}

		return minPlayers;

	}

	public int getMaxPlayers(String raceName) {
		int maxPlayers = 2;

		if (raceData.getConfig().contains("races." + raceName + ".max-players")) {
			maxPlayers = raceData.getConfig().getInt("races." + raceName + ".max-players");
		}

		return maxPlayers;

	}

	public Set<String> getAllRaces() {
		raceData.reloadConfig();
		Set<String> races = new HashSet<String>();
		if (raceData.getConfig().getConfigurationSection("races") != null) {
			races = raceData.getConfig().getConfigurationSection("races").getKeys(false);
		} else {
			races = Collections.<String>emptySet();
		}
		return races;
	}

}
