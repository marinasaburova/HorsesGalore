package me.foreverincolor.horsesgalore.utils;

import me.foreverincolor.horsesgalore.Main;

public class ConfigUtils {

	private static Main plugin;

	// Constructor
	public ConfigUtils(Main plugin) {
		ConfigUtils.plugin = plugin;
	}
	
	// Returns item type from config
	public static String item(String stat) {
		String item;

		item = plugin.getConfig().getString("gui.horse-item");

		return item;
	}
	
}
