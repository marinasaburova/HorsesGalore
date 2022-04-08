package me.foreverincolor.horsesgalore;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.foreverincolor.horsesgalore.commands.GameCommand;
import me.foreverincolor.horsesgalore.commands.GameTabCompleter;
import me.foreverincolor.horsesgalore.commands.HorseCommand;
import me.foreverincolor.horsesgalore.commands.HorseCommandCompleter;
import me.foreverincolor.horsesgalore.commands.RaceCommand;
import me.foreverincolor.horsesgalore.commands.RaceTabCompleter;
import me.foreverincolor.horsesgalore.files.DataManager;
import me.foreverincolor.horsesgalore.listeners.GameProtectListener;
import me.foreverincolor.horsesgalore.listeners.HorseProtectListener;
import me.foreverincolor.horsesgalore.listeners.InventoryClickListener;
import me.foreverincolor.horsesgalore.listeners.PlayerMoveListener;
import me.foreverincolor.horsesgalore.listeners.TameListener;
import me.foreverincolor.horsesgalore.listeners.TrainingListener;
import me.foreverincolor.horsesgalore.managers.GameManager;
import me.foreverincolor.horsesgalore.managers.HorseManager;
import me.foreverincolor.horsesgalore.sql.MySQL;
import me.foreverincolor.horsesgalore.sql.SQLGetter;
import me.foreverincolor.horsesgalore.utils.ConfigUtils;

public class Main extends JavaPlugin implements Listener {

	public MySQL SQL;
	public SQLGetter data;

	public DataManager file;

	private GameManager gameManager;
	private HorseManager horseManager;

	// Actions on plugin enable
	@Override
	public void onEnable() {

		super.onEnable();

		// MINIGAME PART
		gameManager = new GameManager(this);

		// enables listeners
		getServer().getPluginManager().registerEvents(new PlayerMoveListener(gameManager), this);
		getServer().getPluginManager().registerEvents(new GameProtectListener(gameManager), this);

		// enables commands
		getCommand("game").setExecutor(new GameCommand(gameManager));
		getCommand("game").setTabCompleter(new GameTabCompleter());
		getCommand("hrace").setExecutor(new RaceCommand(this, gameManager));
		getCommand("hrace").setTabCompleter(new RaceTabCompleter(this));

		// Loads files
		file = new DataManager(this);

		// HORSES PART
		horseManager = new HorseManager(this);

		// enables listeners
		getServer().getPluginManager().registerEvents(new HorseProtectListener(), this);
		getServer().getPluginManager().registerEvents(new TrainingListener(), this);
		getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
		getServer().getPluginManager().registerEvents(new TameListener(this, horseManager), this);

		// enables commands
		new HorseCommand(this, horseManager);
		getCommand("horse").setTabCompleter(new HorseCommandCompleter());

		// enables config
		new ConfigUtils(this);

		// other
		this.getServer().getPluginManager().registerEvents(this, this);
		saveDefaultConfig();
	}

	// Actions on plugin disable
	@Override
	public void onDisable() {
		file.getConfig().getBoolean("");
		// raceFile.getConfig().getBoolean("");

	}

}
