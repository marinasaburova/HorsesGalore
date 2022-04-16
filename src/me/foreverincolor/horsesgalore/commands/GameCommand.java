package me.foreverincolor.horsesgalore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.foreverincolor.horsesgalore.managers.GameManager;
import me.foreverincolor.horsesgalore.managers.GameState;
import me.foreverincolor.horsesgalore.utils.Utils;

public class GameCommand implements CommandExecutor {

	private GameManager gameManager;

	public GameCommand(GameManager g) {
		gameManager = g;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (label.equalsIgnoreCase("game")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Only players may execute this command!");
				return true;
			}

			Player p = (Player) sender;

			if (args.length == 0) {
				p.sendMessage(Utils.chat("&cThe command was not recognized."));
				return true;
			}

			if (args[0].equalsIgnoreCase("lobby")) {
				gameManager.setGameState(GameState.LOBBY);
				return true;
			}

			if (args[0].equalsIgnoreCase("join")) {
				gameManager.addPlayer(p);
				return true;
			}

			if (args[0].equalsIgnoreCase("quit")) {
				gameManager.removePlayer(p);
				return true;
			}

			if (args[0].equalsIgnoreCase("stop")) {
				gameManager.setGameState(GameState.RESTARTING);
				return true;
			}
			
			if (args[0].equalsIgnoreCase("checkState")) { 
				p.sendMessage(Utils.chat("&bThe game state is: " + gameManager.gameState)); 
				return true; 
			}

			p.sendMessage(Utils.chat("&cThe command was not recognized."));
			return true; 
		}

		return false;

	}
}