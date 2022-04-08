package me.foreverincolor.horsesgalore.commands;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.foreverincolor.horsesgalore.Main;
import me.foreverincolor.horsesgalore.files.RaceCreator;
import me.foreverincolor.horsesgalore.files.RaceDataManager;

public class RaceTabCompleter implements TabCompleter {

	List<String> arguments = new ArrayList<String>();
	Set<String> races = new HashSet<String>();

	private Main plugin;
	private final RaceDataManager raceData;
	RaceCreator race;

	public RaceTabCompleter(Main p) {
		plugin = p;
		raceData = new RaceDataManager(plugin);
		race = new RaceCreator(plugin, raceData);

	}

	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (arguments.isEmpty()) {
			arguments.add("create");
			arguments.add("setmin");
			arguments.add("setmax");
			arguments.add("setdisplayname");
			arguments.add("addstart");
			arguments.add("removestart");
		}

		if (races.isEmpty()) {
		races = race.getAllRaces();
		}
		
		List<String> result = new ArrayList<String>();

		if (args.length == 1) {
			for (String a : arguments) {
				if (a.toLowerCase().startsWith(args[0].toLowerCase())) {
					result.add(a);
				}
			}
			return result;
		}

		// Suggest existing race names
		if ((args.length == 2)) {
			if (args[0].equalsIgnoreCase("setmin") || args[0].equalsIgnoreCase("setmax")
					|| args[0].equalsIgnoreCase("setdisplayname") || args[0].equalsIgnoreCase("addstart")
					|| args[0].equalsIgnoreCase("removestart")) {
				for (String a : races) {
					if (a.toLowerCase().startsWith(args[1].toLowerCase())) {
						result.add(a);
					}
				}
				return result;
			}
		}

		return null;
	}
}
