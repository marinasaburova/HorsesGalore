package me.foreverincolor.horsesgalore.listeners;

import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

import me.foreverincolor.horsesgalore.managers.GameManager;
import me.foreverincolor.horsesgalore.managers.GameState;

public class PlayerMoveListener implements Listener {

	private GameManager gameManager;

	public PlayerMoveListener(GameManager g) {
		gameManager = g;
	}

	Horse horse;

	@EventHandler
	public void onHorseMove(VehicleMoveEvent e) {
		if (gameManager.gameState == GameState.STARTING) {
			if ((e.getVehicle() instanceof Horse) && !(e.getVehicle().isEmpty())) {
				//horse = (Horse) e.getVehicle(); 
			}
		}
	}
}
