package me.foreverincolor.horsesgalore.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

import me.foreverincolor.horsesgalore.Main;
import me.foreverincolor.horsesgalore.files.RaceDataManager;
import me.foreverincolor.horsesgalore.gui.PickHorseGUI;
import me.foreverincolor.horsesgalore.tasks.GameStartCountdownTask;
import me.foreverincolor.horsesgalore.tasks.LobbyCountdownTask;
import me.foreverincolor.horsesgalore.utils.Utils;

public class GameManager {

	private Main plugin;
	private final PlayerManager playerManager;
	private final HorseManager horseManager;
	private final RaceDataManager raceData;
	private GameStartCountdownTask gameStartCountdownTask;
	private LobbyCountdownTask lobbyCountdownTask;

	private ArrayList<Player> players = new ArrayList<Player>();
	private HashMap<Player, Horse> horses = new HashMap<Player, Horse>();

	public GameState gameState = GameState.INACTIVE;

	// Game Constraints
	private int min;
	private int max;
	private List<String> startList = new ArrayList<String>();

	// CONSTRUCTOR
	public GameManager(Main plugin) {
		this.plugin = plugin;

		this.playerManager = new PlayerManager(this);
		this.horseManager = new HorseManager(plugin);
		this.raceData = new RaceDataManager(plugin);

		loadData();

	}

	public void setGameState(GameState s) {
		if (gameState == s) {
			return;
		}

		gameState = s;

		switch (gameState) {
		case INACTIVE:
			break;

		case LOBBY:
			Bukkit.broadcastMessage(Utils.chat("&a&lLOBBY STATE!"));
			loadData();
			checkTimer(); // check if enough players

			break;

		case STARTING:
			Bukkit.broadcastMessage(Utils.chat("&a&lThe game is STARTING!"));
			loadData();
			tpRacers();

			// countdown
			gameStartCountdownTask = new GameStartCountdownTask(this);
			gameStartCountdownTask.runTaskTimer(plugin, 0, 20);

			break;

		case ACTIVE:
			if (gameStartCountdownTask != null) {
				gameStartCountdownTask.cancel();
			}

			Bukkit.broadcastMessage(Utils.chat("&a&lThe game is ACTIVE!"));
			break;

		case WON:
			Bukkit.broadcastMessage(Utils.chat("&c&lThe game is finished!"));
			break;

		case RESTARTING:
			cleanup();
			Bukkit.broadcastMessage(Utils.chat("&bThe game has been stopped."));
			gameState = GameState.INACTIVE;
			break;
		}

	}

	// resets game
	public void cleanup() {
		players.clear();
		horses.clear();
		lobbyCountdownTask = null;
		gameStartCountdownTask = null;
	}

	// Adds a player to the game
	public void addPlayer(Player p) {
		if (gameState == GameState.LOBBY) {

			if (players.contains(p)) {
				p.sendMessage(Utils.chat("&bYou are already in the game"));
				return;
			}

			if (players.size() < max) {
				// display GUI to give horse selection
				PickHorseGUI gui = new PickHorseGUI(plugin, this, horseManager);
				p.openInventory(gui.viewRaceHorses(p));
			} else {
				p.sendMessage(Utils.chat("&cThe game is full."));
			}

		} else {
			p.sendMessage(Utils.chat("&cThe game is currently not accepting new players."));
		}
	}

	// adds horse & player to game
	public void addHorse(Player p, Horse h) {
		if (gameState == GameState.LOBBY) {
			horses.put(p, h);

			// add player to queue
			players.add(p);
			Bukkit.broadcastMessage(Utils.chat(p.getDisplayName() + " has joined the game!"));
			checkTimer();
		}
	}

	// removes player from game
	public void removePlayer(Player p) {
		players.remove(p);
		horses.remove(p);
		if (players.contains(p)) {
			p.sendMessage(Utils.chat("&cAn error occurred when trying to remove you."));
		} else {
			Bukkit.broadcastMessage(Utils.chat("&b" + p.getDisplayName() + " has left the game."));
		}
		checkTimer();
	}

	// Controls the timer with regards to players joining/quitting and game state
	// changing
	private void checkTimer() {
		Bukkit.broadcastMessage(Utils.chat("Number of players: " + players.size()));

		if (players.size() >= min) {
			if (lobbyCountdownTask == null) {
				lobbyCountdownTask = new LobbyCountdownTask(this);
				lobbyCountdownTask.runTaskTimer(plugin, 0, 20);
			}
		} else {
			Bukkit.broadcastMessage(Utils.chat("&c&lNot Enough Players..."));
			if (lobbyCountdownTask != null) {
				lobbyCountdownTask.cancel();
				lobbyCountdownTask = null;
			}
		}
	}

	// tp players & horses to spots
	private void tpRacers() {
		for (int j = 0; j < players.size(); j++) {
			players.get(j).teleport(Utils.getLoc(startList.get(j)));
			horses.get(players.get(j)).teleport(Utils.getLoc(startList.get(j)));
			horses.get(players.get(j)).addPassenger(players.get(j));
		}
	}

	// load data from config
	private void loadData() {
		String raceName = "race1";

		min = raceData.getConfig().getInt("races." + raceName + ".min-players");
		max = raceData.getConfig().getInt("races." + raceName + ".max-players");
		startList = raceData.getConfig().getStringList("races." + raceName + ".start-locations");
	}

	public PlayerManager getPlayerManager() {
		return playerManager;
	}
}