package me.foreverincolor.horsesgalore.listeners;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import me.foreverincolor.horsesgalore.utils.Utils;

public class TrainingListener implements Listener {

	// Constructor
	private boolean isMounted = false;

	public TrainingListener() {
	}

	// Listens for player mounting horse
	@EventHandler
	public void onMount(VehicleEnterEvent e) {

		// Only works for horses
		if ((e.getVehicle() instanceof Horse) && (e.getEntered() instanceof Player)) {
			Horse horse = (Horse) e.getVehicle();
			Player p = (Player) e.getEntered();

			// Makes sure horse is tamed
			if (horse.isTamed() && (horse.getOwner() == p)) {
				// Begin improving stats
				isMounted = true;
				p.sendMessage(Utils.chat("&5Horse training is now beginning..."));
			}
		}
	}

	// Listens for horse jumping
	@EventHandler
	public void onJump(HorseJumpEvent e) {
		if (isMounted) {
			// listen for jumps
			Horse horse = (Horse) e.getEntity();
			Player p = (Player) horse.getOwner();

			double jump = horse.getJumpStrength();

			// Jump strength ranges: 0.4 - 1.0

			if (jump >= 1.0) {
				return;
			}

			// generate random number
			int chance = (int) ((Math.random() * (10)));

			// random chance of leveling up
			if (chance == 1) {
				p.sendMessage(Utils.chat("&2Previous jump strength: " + String.format("%.4f", jump)));
				jump += .0001;
				horse.setJumpStrength(jump);
				p.sendMessage(Utils.chat("&2Your horse has leveled up in jump strength!"));
				p.sendMessage(Utils.chat("&2New jump strength: " + String.format("%.4f", jump)));
			}

		}
	}

	// Listens for horse running
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (e.getPlayer().getVehicle() instanceof Horse) {
			if (isMounted) {
				// listen for movement
				Horse horse = (Horse) e.getPlayer().getVehicle();
				Player p = (Player) horse.getOwner();
				double speed = horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue();

				// Speed ranges .1125 - .3375
				if (speed >= 0.3375) {
					return;
				}

				// generate random number
				int chance = (int) ((Math.random() * (100)));

				// random chance of leveling up
				if (chance == 1) {
					p.sendMessage(Utils.chat("&2Previous speed: " + String.format("%.4f", speed)));
					speed += .0001;
					horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
					p.sendMessage(Utils.chat("&2Your horse has leveled up in speed!"));
					p.sendMessage(Utils.chat("&2New speed: " + String.format("%.4f", speed)));

				}
			}
		}
	}
}
