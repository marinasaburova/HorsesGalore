package me.foreverincolor.horsesgalore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import me.foreverincolor.horsesgalore.managers.GameManager;
import me.foreverincolor.horsesgalore.managers.GameState;

public class LobbyCountdownTask extends BukkitRunnable {

	private GameManager gameManager;
	private int timeLeft = 31; // read from config ?

	public LobbyCountdownTask(GameManager g) {
		gameManager = g;
	}

	@Override
	public void run() {
		if (gameManager.gameState != GameState.LOBBY) {
			cancel();
			return;
		}

		timeLeft--;

		if (timeLeft <= 0) {
			cancel();
			gameManager.setGameState(GameState.STARTING);
			return;
		}

		if ((timeLeft == 30) || (timeLeft == 15) || (timeLeft == 10)) {
			Bukkit.broadcastMessage(timeLeft + " seconds left");
		}

		if (timeLeft <= 5) {
			Bukkit.broadcastMessage(timeLeft + " seconds left");
		}

	}

}
