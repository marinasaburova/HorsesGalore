package me.foreverincolor.horsesgalore.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class HorseCommandCompleter implements TabCompleter {

	List<String> arguments = new ArrayList<String>();

	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (arguments.isEmpty()) {
			arguments.add("name");
			arguments.add("debug");
			arguments.add("speed");	// remove later
			arguments.add("jump");	// remove later

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

		return null;
	}
}
