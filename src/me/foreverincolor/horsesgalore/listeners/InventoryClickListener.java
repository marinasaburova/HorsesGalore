package me.foreverincolor.horsesgalore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.foreverincolor.horsesgalore.gui.HorseDetailsGUI;
import me.foreverincolor.horsesgalore.gui.HorsesGUI;
import me.foreverincolor.horsesgalore.gui.MinigameGUI;
import me.foreverincolor.horsesgalore.gui.PickHorseGUI;

/* 
 * Listens for various inventory click functions
 */

public class InventoryClickListener implements Listener {

	// CONSTRUCTOR
	public InventoryClickListener() {

	}

	// Listens for player clicks in profile GUI
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		String title = e.getView().getTitle();

		// For Horses GUI
		if (title.contentEquals(HorsesGUI.inventory_name)) {
			e.setCancelled(true);

			if (e.getCurrentItem() == null) {
				return;
			}

			if (title.equals(HorsesGUI.inventory_name)) {
				HorsesGUI.clicked((Player) e.getWhoClicked(), e.isRightClick(), e.getSlot(), e.getCurrentItem(),
						e.getInventory());
				return;
			}

		}

		// For Horse Selection GUI
		if (title.contentEquals(PickHorseGUI.inventory_name)) {
			e.setCancelled(true);

			if (e.getCurrentItem() == null) {
				return;
			}

			if (title.equals(PickHorseGUI.inventory_name)) {
				PickHorseGUI.clicked((Player) e.getWhoClicked(), e.isRightClick(), e.getSlot(), e.getCurrentItem(),
						e.getInventory());
				return;
			}
		}

		// For Horse Details GUI
		if (title.contentEquals(HorseDetailsGUI.inventory_name)) {
			e.setCancelled(true);

			if (e.getCurrentItem() == null) {
				return;
			}

			if (title.equals(HorseDetailsGUI.inventory_name)) {
				HorseDetailsGUI.clicked((Player) e.getWhoClicked(), e.isRightClick(), e.getSlot(), e.getCurrentItem(),
						e.getInventory());
				return;
			}

		}
		
		if (title.contentEquals(MinigameGUI.inventory_name)) { 
			e.setCancelled(true);
		}

	}

}
