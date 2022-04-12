package me.foreverincolor.horsesgalore;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.foreverincolor.horsesgalore.commands.GameCommand;
import me.foreverincolor.horsesgalore.commands.GameTabCompleter;
import me.foreverincolor.horsesgalore.commands.HorseCommand;
import me.foreverincolor.horsesgalore.commands.HorseCommandCompleter;
import me.foreverincolor.horsesgalore.commands.RaceCommand;
import me.foreverincolor.horsesgalore.commands.RaceTabCompleter;
import me.foreverincolor.horsesgalore.files.HorseData;
import me.foreverincolor.horsesgalore.listeners.GameProtectListener;
import me.foreverincolor.horsesgalore.listeners.HorseProtectListener;
import me.foreverincolor.horsesgalore.listeners.InventoryClickListener;
import me.foreverincolor.horsesgalore.listeners.PlayerMoveListener;
import me.foreverincolor.horsesgalore.listeners.TameListener;
import me.foreverincolor.horsesgalore.listeners.TrainingListener;
import me.foreverincolor.horsesgalore.managers.GameManager;
import me.foreverincolor.horsesgalore.managers.HorseManager;
import me.foreverincolor.horsesgalore.managers.RaceManager;
import me.foreverincolor.horsesgalore.sql.MySQL;
import me.foreverincolor.horsesgalore.sql.SQLGetter;
import me.foreverincolor.horsesgalore.utils.ConfigUtils;

public class Main extends JavaPlugin implements Listener {

	public MySQL SQL;
	public SQLGetter data;

	public HorseData file;

	private GameManager gameManager;
	private HorseManager horseManager;
	private RaceManager raceManager;

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

		// RACES PART
		raceManager = new RaceManager(this);

		// enables commands
		getCommand("hrace").setExecutor(new RaceCommand(this, raceManager));
		getCommand("hrace").setTabCompleter(new RaceTabCompleter(this, raceManager));

		// HORSES PART
		horseManager = new HorseManager(this);

		// enables listeners
		getServer().getPluginManager().registerEvents(new HorseProtectListener(), this);
		getServer().getPluginManager().registerEvents(new TrainingListener(), this);
		getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
		getServer().getPluginManager().registerEvents(new TameListener(this, horseManager), this);

		// enables commands
		getCommand("horse").setExecutor(new HorseCommand(this, horseManager));
		getCommand("horse").setTabCompleter(new HorseCommandCompleter());

		// other
		file = new HorseData(this);
		new ConfigUtils(this);
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
