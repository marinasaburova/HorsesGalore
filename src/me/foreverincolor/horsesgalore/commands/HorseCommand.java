package me.foreverincolor.horsesgalore.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.foreverincolor.horsesgalore.Main;
import me.foreverincolor.horsesgalore.gui.HorsesGUI;
import me.foreverincolor.horsesgalore.managers.HorseManager;
import me.foreverincolor.horsesgalore.utils.Utils;

public class HorseCommand implements CommandExecutor {

	private Main plugin;
	private HorseManager horseManager;

	// Constructors
	public HorseCommand(Main plugin, HorseManager h) {
		this.plugin = plugin;
		this.horseManager = h;

		plugin.getCommand("horse").setExecutor(this);
	}

	// Different actions based on commands
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player p = (Player) sender;

		if (label.equalsIgnoreCase("horse")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage("Only players may execute this command!");
				return true;
			}

			// View your horses
			if (args.length == 0) {
				HorsesGUI inv = new HorsesGUI(plugin, horseManager);
				p.openInventory(inv.viewHorses(p));
				return true;
			}

			// Name your horse
			if (args[0].equalsIgnoreCase("name")) {

				if (args.length != 2) {
					p.sendMessage(Utils.chat("&cUsage: /horse name <Name>"));
					return true;
				}

				ItemStack nameItem = Utils.createItem("NAME_TAG", "&atype &e&l/horse name &ato name your horse");

				// check if player has the special name tag
				if (p.getInventory().containsAtLeast(nameItem, 1)) {

					// remove current item
					p.getInventory().removeItem(nameItem);

					// rename name tag
					ItemMeta meta = nameItem.getItemMeta();
					meta.setDisplayName(args[1]);
					nameItem.setItemMeta(meta);

					// give new item
					p.getInventory().addItem(nameItem);

					p.sendMessage(Utils.chat("&dRenamed your name tag."));

				} else {
					p.sendMessage(Utils.chat("&cYou need to have the special name tag in your inventory!"));
				}

				return true;
			}

			// debug apple
			if (args[0].equalsIgnoreCase("debug")) {
				if (args.length != 1) {
					p.sendMessage(Utils.chat("&cUsage: /horse debug"));
				}
				ItemStack item = Utils.createNamedItem("APPLE", "&a&lDebug Apple",
						"&aright click on a horse with this apple to add them to your inventory!");
				p.getInventory().addItem(item);
				p.sendMessage(Utils.chat("&bYou have received your debug apple!"));
				return true;
			}

			// REMOVE LATER, for DEBUG
			if (args[0].equalsIgnoreCase("speed")) {
				Horse horse = (Horse) Bukkit.getEntity(UUID.fromString(args[1]));
				double speed = Double.parseDouble(args[2]);
				horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
				p.sendMessage("&dYour horse speed has been updated.");
				return true; 
			}

			// REMOVE LATER, for DEBUG
			if (args[0].equalsIgnoreCase("jump")) {
				Horse horse = (Horse) Bukkit.getEntity(UUID.fromString(args[1]));
				double jump = Double.parseDouble(args[2]);
				horse.setJumpStrength(jump);
				p.sendMessage("&dYour horse jump strength has been updated.");
				return true; 
			}
			
			p.sendMessage(Utils.chat("&cThe command was not recognized."));
			return true; 

		}

		return false;
	}
}
