package me.foreverincolor.horsesgalore.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.ChestedHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import me.foreverincolor.horsesgalore.Main;
import me.foreverincolor.horsesgalore.managers.HorseManager;
import me.foreverincolor.horsesgalore.utils.Utils;

public class TameListener implements Listener {

	// Constructor
	private Main plugin;
	private HorseManager horseManager;

	public TameListener(Main plugin, HorseManager h) {
		this.plugin = plugin;
		horseManager = h;
	}

	// Listens for player taming horse
	@EventHandler
	public void onTame(EntityTameEvent e) {
		Entity tamed = e.getEntity();

		// Weeds out all non-horses
		if (tamed instanceof AbstractHorse || tamed instanceof Horse) {
			Player p = (Player) e.getOwner();

			if (horseManager.addHorse(p, (Horse) tamed)) {
				// send message
				p.sendMessage(Utils.chat("&aYou have tamed a horse!"));
				p.sendMessage(Utils.chat("&aUse the name tag to name your horse!"));

				// give player nametag to rename horse
				p.getInventory().addItem(Utils.createItem("NAME_TAG", "&atype &e&l/horse name &ato name your horse"));

			} else {
				// does not tame horse
				p.sendMessage(Utils.chat("&cYou have reached your horse tame limit"));
				e.setCancelled(true);
			}
		}
	}

	// listen for interactions with previously tamed horses & add to list if not
	// there
	@EventHandler
	public void onClick(PlayerInteractEntityEvent e) {
		boolean isFound = false;

		Entity clicked = e.getRightClicked();
		Player clicker = e.getPlayer();
		ItemStack debugApple = Utils.createNamedItem("APPLE", "&a&lDebug Apple",
				"&aright click on a horse with this apple to add them to your inventory!");

		// add clicker is holding debug apple
		if ((clicked instanceof AbstractHorse || clicked instanceof Horse || clicked instanceof ChestedHorse)
				&& (clicker.getInventory().getItemInMainHand().equals(debugApple))) {
			AbstractHorse horse = (AbstractHorse) clicked;

			if (horse.isTamed()) {
				OfflinePlayer p = (OfflinePlayer) horse.getOwner();
				String horseUUID = horse.getUniqueId().toString();

				List<String> horseList = new ArrayList<String>();
				horseList = horseManager.getHorseList(p);

				String horse2UUID;
				for (int i = 0; i < horseList.size(); i++) {
					horse2UUID = horseList.get(i);
					if (horseUUID.equalsIgnoreCase(horse2UUID)) {
						isFound = true;
						((Player) p).sendMessage(Utils.chat("&bThis horse is already in your inventory."));
						return;
					}
				}

				if (!isFound) {
					if (horseManager.addHorse(p, (Horse) horse)) {
						((Player) p).sendMessage(Utils.chat("&aThis horse has been added to your inventory!"));

					} else {
						clicker.sendMessage(
								Utils.chat("The tame limit is " + plugin.getConfig().getInt("taming.limit")));
						clicker.sendMessage(
								Utils.chat("&cYou have reached your horse tame limit. This horse is now untamed."));
						horse.setOwner(null);
						e.setCancelled(true);
						return;
					}
				}
			}
		}
	}

	// Removes dead horses from inventory
	@EventHandler
	public void onHorseDeath(EntityDeathEvent e) {
		Entity dead = e.getEntity();

		// Weeds out all non-horses
		if (dead instanceof AbstractHorse || dead instanceof Horse) {
			Horse horse = (Horse) dead;
			Player p = (Player) horse.getOwner();

			// removes horse
			horseManager.removeHorse(p, horse);

			// send message
			p.sendMessage(Utils.chat("&bYour dead horse has been removed from your gui"));
			p.sendMessage(Utils.chat("&bRIP."));
		}

	}

}
