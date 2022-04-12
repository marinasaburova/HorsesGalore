package me.foreverincolor.horsesgalore.files;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;

import me.foreverincolor.horsesgalore.Main;

public class RaceCreator {

	@SuppressWarnings("unused")
	private Main plugin;
	private RaceDataManager file;

	public RaceCreator(Main plugin, RaceDataManager file) {
		this.plugin = plugin;
		this.file = file;
	}

	/*
	 * CREATE RACE
	 */

	public void createRace(String raceName) {
		file.getConfig().set("races." + raceName, raceName);
		file.saveConfig();

		setMinPlayers(raceName, 2);
		setMaxPlayers(raceName, 8);
	}

	public void createRace(String raceName, int maxPlayers) {
		file.getConfig().set("races." + raceName, raceName);
		file.saveConfig();

		setMinPlayers(raceName, 2);
		setMaxPlayers(raceName, maxPlayers);

	}

	public void createRace(String raceName, int minPlayers, int maxPlayers) {
		file.getConfig().set("races." + raceName, raceName);
		file.saveConfig();

		setMinPlayers(raceName, minPlayers);
		setMaxPlayers(raceName, maxPlayers);

	}

	public void createRace(String raceName, String displayName, int minPlayers, int maxPlayers) {
		file.getConfig().set("races." + raceName, raceName);
		file.saveConfig();

		setMinPlayers(raceName, minPlayers);
		setMaxPlayers(raceName, maxPlayers);
		setDisplayName(raceName, displayName);

	}

	/*
	 * SETTERS
	 */

	public void setDisplayName(String raceName, String displayName) {
		file.getConfig().set("races." + raceName + ".display-name", displayName);
		file.saveConfig();

	}

	public void setMinPlayers(String raceName, int minPlayers) {
		file.getConfig().set("races." + raceName + ".min-players", minPlayers);
		file.saveConfig();

	}

	public void setMaxPlayers(String raceName, int maxPlayers) {
		file.getConfig().set("races." + raceName + ".max-players", maxPlayers);
		file.saveConfig();

	}

	public void addStart(String raceName, String loc) {
		List<String> startList = new ArrayList<String>();
		startList = file.getConfig().getStringList("races." + raceName + ".start-locations");
		startList.add(loc);
		file.getConfig().set("races." + raceName + ".start-locations", startList);
		file.saveConfig();

	}

	public void removeStart(String raceName, int num) {
		List<String> startList = new ArrayList<String>();
		startList = file.getConfig().getStringList("races." + raceName + ".start-locations");
		startList.remove(num);
		file.getConfig().set("races." + raceName + ".start-locations", startList);
		file.saveConfig();
	}

	public void setFinish(String raceName, Location loc) {
		;
		file.getConfig().set("races." + raceName + ".finish-location", loc.toString());
		file.saveConfig();

	}

	/*
	 * GETTERS
	 */

	public String getDisplayName(String raceName) {
		String displayName = raceName;

		if (file.getConfig().contains("races." + raceName + ".display-name")) {
			displayName = file.getConfig().getString("races." + raceName + ".display-name");
		}

		return displayName;

	}

	public int getMinPlayers(String raceName) {
		int minPlayers = 2;

		if (file.getConfig().contains("races." + raceName + ".min-players")) {
			minPlayers = file.getConfig().getInt("races." + raceName + ".min-players");
		}

		return minPlayers;

	}

	public int getMaxPlayers(String raceName) {
		int maxPlayers = 2;

		if (file.getConfig().contains("races." + raceName + ".max-players")) {
			maxPlayers = file.getConfig().getInt("races." + raceName + ".max-players");
		}

		return maxPlayers;

	}

	public Set<String> getAllRaces() {
		Set<String> races = new HashSet<String>();
		if (file.getConfig().getConfigurationSection("races") != null) {
			races = file.getConfig().getConfigurationSection("races").getKeys(false);
		} else {
			races = Collections.<String>emptySet();
		}
		return races;
	}

}
