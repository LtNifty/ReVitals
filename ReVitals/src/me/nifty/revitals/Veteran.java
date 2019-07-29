package me.nifty.revitals;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Veteran {

	private Main plugin = Main.getPlugin(Main.class);
	private FileConfiguration config;
	private Player player;
	private File file;

	public Veteran(Player p) {
		this.player = p;
		this.file = new File(plugin.getDataFolder() + File.separator + "playtime_old.yml");
		this.config = YamlConfiguration.loadConfiguration(file);
	}

	public void processStatus() {
		if (plugin.getConfig().getBoolean("veteran.use"))
			if (this.file.exists()) {
				String uuid = this.player.getUniqueId().toString();
				if (this.config.contains(uuid)) {
					this.config.set(uuid + ".taken", true);
					this.saveData();
					int oldPlaytime = this.config.getInt(uuid + ".playtime")/(20*60*60);
					new PlayerDataHandler(this.player).set("Old PlayTime", oldPlaytime);
					if (oldPlaytime >= plugin.getConfig().getInt("veteran-time-req")) {
						Main.permission.playerAddGroup(null, player, "Veteran");
						player.sendMessage(ChatColor.GOLD + "Found your old player data! You are now a Veteran!");
					} else
						player.sendMessage(ChatColor.GOLD + "This server offers a Veteran rank for those who played on the original 6d7 server. If you believe you qualify, please respectfully PM an admin.");
				} else 
					Bukkit.getLogger().log(Level.WARNING, "Unable to find playtime_old.yml! Cannot check veteran status without it!");
			}
	}

	public void saveData() {
		try {
			this.config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
