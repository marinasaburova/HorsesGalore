package me.foreverincolor.horsesgalore.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;

import me.foreverincolor.horsesgalore.Main;

public class SQLGetter {

	private Main plugin;

	// Constructor
	public SQLGetter(Main plugin) {
		this.plugin = plugin;
	}

	// Creates a table
	public void createTable() {
		PreparedStatement ps;

		try {
			ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE ..."); // FINISH 
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Creates new player (if does not exist)
	public void createPlayer(Player p) {
		try {
			UUID uuid = p.getUniqueId();

			// if player does not exist, create
			if (!exists(uuid)) {
				PreparedStatement ps = plugin.SQL.getConnection()
						.prepareStatement("INSERT IGNORE INTO player (name, uuid) VALUES (?,?)");
				ps.setString(1, p.getName());
				ps.setString(2, uuid.toString());
				ps.executeUpdate();

				return;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Checks if player exists with given UUID
	public boolean exists(UUID uuid) {
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM player WHERE uuid=?");
			ps.setString(1, uuid.toString());

			ResultSet results = ps.executeQuery();
			if (results.next()) {
				// player is found
				return true;
			}
			return false;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// Returns player's name
	public String getName(UUID uuid) {
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT name FROM player WHERE uuid=?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();

			String name = "";
			if (rs.next()) {
				name = rs.getString("name");
			}
			return name;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	// Returns player's uuid
	public UUID getUUID(String name) {
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT uuid FROM player WHERE name=?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();

			UUID uuid;

			// if uuid is found
			if (rs.next()) {
				try {
					uuid = UUID.fromString(rs.getString("uuid"));
					return uuid;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
