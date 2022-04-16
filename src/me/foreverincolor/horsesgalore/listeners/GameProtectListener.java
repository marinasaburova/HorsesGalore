package me.foreverincolor.horsesgalore.listeners;

import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

import me.foreverincolor.horsesgalore.managers.GameManager;
import me.foreverincolor.horsesgalore.managers.GameState;

public class GameProtectListener implements Listener {

	private GameManager gameManager;

	public GameProtectListener(GameManager g) {
		gameManager = g;
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (gameManager.gameState != GameState.INACTIVE) {
			e.setCancelled(true);
		} else
			return;
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (gameManager.gameState != GameState.INACTIVE) {
			e.setCancelled(true);
		} else
			return;
	}

	@EventHandler
	public void onGameDismount(EntityDismountEvent e) {
		if ((gameManager.gameState == GameState.ACTIVE) || gameManager.gameState == GameState.STARTING) {
			if ((e.getDismounted() instanceof Horse || e.getDismounted() instanceof AbstractHorse)
					&& (e.getEntity() instanceof Player)) {
				e.setCancelled(true);
			}
		}
	}

}
