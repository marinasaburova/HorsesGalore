package me.foreverincolor.horsesgalore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import me.foreverincolor.horsesgalore.managers.GameManager;
import me.foreverincolor.horsesgalore.managers.GameState;

public class GameStartCountdownTask extends BukkitRunnable {

	private GameManager gameManager;
	private int timeLeft = 6;

	public GameStartCountdownTask(GameManager g) {
		gameManager = g;
	}

	@Override
	public void run() {
		if (gameManager.gameState != GameState.STARTING) {
			cancel();
			return;
		}

		timeLeft--;

		if (timeLeft <= 0) {
			cancel();
			gameManager.setGameState(GameState.ACTIVE);
			return;
		}

		Bukkit.broadcastMessage(timeLeft + " seconds");

	}

}
