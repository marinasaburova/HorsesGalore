package me.foreverincolor.horsesgalore.listeners;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.ChestedHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.inventory.AbstractHorseInventory;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import me.foreverincolor.horsesgalore.utils.Utils;

/*
 * Protects tamed horses from various damage by other players
 */

public class HorseProtectListener implements Listener {

	// constructor
	public HorseProtectListener() {
	}

	// Prevents damage by other players
	@EventHandler
	public void onHurt(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Horse || e.getEntity() instanceof AbstractHorse
				|| e.getEntity() instanceof ChestedHorse) {

			AbstractHorse horse = (AbstractHorse) e.getEntity();
			Entity damager = e.getDamager();

			if (horse.isTamed() && (damager instanceof Player)) {
				e.setCancelled(true);
				e.setDamage(0);

				damager.sendMessage(Utils.chat("&cYou cannot hurt tamed horses!"));
			}

			if (horse.isTamed() && (damager instanceof Projectile)) {

				Projectile projectile = (Projectile) e.getDamager();
				ProjectileSource source = projectile.getShooter();

				if (source instanceof Player) {
					e.setCancelled(true);
					e.setDamage(0);

					damager.sendMessage(Utils.chat("&cYou cannot shoot tamed horses!"));
				}
			}
		}
	}

	// Prevents others from mounting
	@EventHandler
	public void onMount(VehicleEnterEvent e) {
		if (e.getVehicle() instanceof Horse || e.getVehicle() instanceof AbstractHorse
				|| e.getVehicle() instanceof ChestedHorse) {

			AbstractHorse horse = (AbstractHorse) e.getVehicle();
			Entity passenger = e.getEntered();

			if (horse.isTamed() && (passenger instanceof Player)) {
				OfflinePlayer owner = (OfflinePlayer) horse.getOwner();

				if (passenger.getUniqueId() != owner.getUniqueId()) {
					e.setCancelled(true);

					passenger.sendMessage(Utils.chat("&cYou cannot ride " + owner.getName() + "'s horse!"));
				}
			}
		}
	}

	// Prevents others from opening inventory
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent e) {
		Inventory inventory = e.getInventory();
		Player p = (Player) e.getPlayer();

		if (inventory instanceof HorseInventory || e.getInventory() instanceof AbstractHorseInventory
				|| e.getInventory() instanceof ChestedHorse) {
			AbstractHorse horse = (AbstractHorse) inventory.getHolder();

			if (horse.isTamed()) {
				OfflinePlayer owner = (OfflinePlayer) horse.getOwner();

				if (p.getUniqueId() != owner.getUniqueId()) {
					e.setCancelled(true);

					p.sendMessage(Utils.chat("&cYou cannot open " + owner.getName() + "'s horse's inventory!"));

				}
			}
		}
	}

	// Prevents others from leading away your horse
	@EventHandler
	public void onLead(PlayerInteractEntityEvent e) {
		// only for horses
		if (e.getRightClicked() instanceof Horse) {
			Player p = e.getPlayer();
			Horse h = (Horse) e.getRightClicked();

			ItemStack lead = Utils.createItem("LEAD");

			if ((h.isTamed()) && (p.getInventory().getItemInMainHand().equals(lead))) {
				if (h.getOwner() != p) {
					e.setCancelled(true);
					p.sendMessage(Utils.chat("&cYou cannot lead other player's horses!"));
				}
			}
		}
	}

}
