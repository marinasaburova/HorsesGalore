package me.foreverincolor.horsesgalore.commands;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.foreverincolor.horsesgalore.Main;
import me.foreverincolor.horsesgalore.managers.RaceManager;
import me.foreverincolor.horsesgalore.utils.Utils;

public class RaceCommand implements CommandExecutor {

	@SuppressWarnings("unused")
	private Main plugin;
	private RaceManager raceManager;

	// Constructors
	public RaceCommand(Main plugin, RaceManager r) {
		this.plugin = plugin;
		this.raceManager = r;

		plugin.getCommand("hrace").setExecutor(this);

	}

	// Different actions based on commands
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player p = (Player) sender;

		if (label.equalsIgnoreCase("hrace")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Only players may execute this command!");
				return true;
			}

			// View all available races
			if (args.length == 0) {
				p.sendMessage(Utils.chat("&dIn the future, you will see a list of all races here."));

				return true;
			}

			// REMOVE LATER, for DEBUG
			if (args[0].equalsIgnoreCase("list")) {
				Set<String> races = new HashSet<String>();
				races = raceManager.getAllRaces();
				p.sendMessage(Utils.chat("&7Your races are: &e"));
				for (String r : races) {
					p.sendMessage(r);
				}
				return true;
			}

			// CREATE RACE
			if (args[0].equalsIgnoreCase("create")) {

				// command: /hrace create <name>
				if (args.length != 2) {
					p.sendMessage(Utils.chat("&cUsage: /hrace create <race-name>"));
					return true;
				}

				if (raceManager.createRace(args[1])) {
					p.sendMessage(Utils.chat("&aYour race " + args[1] + " &ahas been created"));
				} else {
					p.sendMessage(Utils.chat("&cThis race already exists!"));
				}
				return true;
			}

			// SET MINIMUM PLAYERS
			if (args[0].equalsIgnoreCase("setmin")) {

				if (args.length != 3) {
					p.sendMessage(Utils.chat("&cUsage: /hrace setmin <race-name> <min-players>"));
					return true;
				}

				int min = 2;

				// check for valid input
				try {
					min = Integer.parseInt(args[2]);
				} catch (NumberFormatException e) {
					p.sendMessage(Utils.chat("&cPlease enter a valid number for minimum players"));
					return true;
				}

				if (min < 1) {
					p.sendMessage(Utils.chat("&cPlease enter a valid number for minimum players"));
					return true;
				}

				raceManager.setMinPlayers(args[1], min);
				p.sendMessage(Utils.chat("&aMinimum players for " + args[1] + " &ahas been set to " + min));
				return true;
			}

			// SET MAXIMUM PLAYERS
			if (args[0].equalsIgnoreCase("setmax")) {

				if (args.length != 3) {
					p.sendMessage(Utils.chat("&cUsage: /hrace setmax <race-name> <max-players>"));
					return true;
				}

				int max = 8;

				// check for valid input
				try {
					max = Integer.parseInt(args[2]);
				} catch (NumberFormatException e) {
					p.sendMessage(Utils.chat("&cPlease enter a valid number for maximum players"));
					return true;
				}

				if (max < 1) {
					p.sendMessage(Utils.chat("&cPlease enter a valid number for maximum players"));
					return true;
				}

				raceManager.setMaxPlayers(args[1], max);
				p.sendMessage(Utils.chat("&aMaximum players for " + args[1] + " &ahas been set to " + max));
				return true;
			}

			// SET DISPLAY NAME
			if (args[0].equalsIgnoreCase("setdisplayname")) {

				if (args.length != 3) {
					p.sendMessage(Utils.chat("&cUsage: /hrace setdisplayname <race-name> <display-name>"));
					return true;
				}

				raceManager.setDisplayName(args[1], args[2]);
				p.sendMessage(Utils.chat("&aDisplay name for " + args[1] + " &ahas been set to " + args[2]));
				return true;
			}

			// ADD START POSITION
			if (args[0].equalsIgnoreCase("addstart")) {
				if (args.length != 2) {
					p.sendMessage(Utils.chat("&cUsage: /hrace addstart <race-name>"));
					return true;
				}

				String world = p.getLocation().getWorld().getName();
				int x = p.getLocation().getBlockX();
				int y = p.getLocation().getBlockY();
				int z = p.getLocation().getBlockZ();

				String location = world + " " + x + " " + y + " " + z;

				raceManager.addStart(args[1], location);
				p.sendMessage(Utils.chat("&aA starting location has been added to " + args[1]));
				return true;
			}

			// REMOVE START POSITION
			if (args[0].equalsIgnoreCase("removestart")) {
				if (args.length != 3) {
					p.sendMessage(Utils.chat("&cUsage: /hrace removestart <race-name> <start-index>"));
					return true;
				}

				// Check for valid input

				try {
					raceManager.removeStart(args[1], Integer.parseUnsignedInt(args[2]));
					p.sendMessage(Utils.chat("&aA starting location has been removed from " + args[1]));
				} catch (NumberFormatException e) {
					p.sendMessage(Utils.chat("&cPlease enter a valid index"));
				}
				return true;
			}

			p.sendMessage(Utils.chat("&cThe command was not recognized."));
			return true;

		}

		return false;
	}
}
