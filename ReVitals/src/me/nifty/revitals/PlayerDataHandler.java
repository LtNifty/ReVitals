package me.nifty.revitals;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class PlayerDataHandler {

	private Main plugin = Main.getPlugin(Main.class);
	private Player player;
	private FileConfiguration config;
	private File file;

	public PlayerDataHandler(Player p) {
		this.player = p;
		this.file = new File(plugin.getDataFolder() + File.separator + "userdata" + File.separator + p.getUniqueId().toString() + ".yml");
		this.config = YamlConfiguration.loadConfiguration(file);
	}

	public void newPlayer() {
		if (!this.file.exists()) {
			try {
				this.file.createNewFile();
				this.config.set("UUID", this.player.getUniqueId().toString());
				this.config.set("Name", this.player.getName());
				this.config.set("Display Name", this.player.getDisplayName());
				this.config.set("IP Address", this.player.getAddress().getAddress().getHostAddress());
				this.config.set("Balance", Main.economy.getBalance(this.player));
				this.config.createSection("Metaboard");
				this.config.set("Metaboard.Item Name", "");
				this.config.set("Metaboard.Lore", "");
				this.config.set("Metaboard.Enchants", "");
				this.config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public FileConfiguration getConfig() {
		return this.config;
	}

	public void saveData() {
		try {
			this.config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// you have to save every time the config is changed

	public void setDisplayName(String s) {
		this.config.set("Display Name", s);
		this.saveData();
	}

	public void setMetaItemName(String s) {
		this.config.set("Metaboard.Item Name", s);
		this.saveData();
	}

	public void setMetaLore(List<String> arr) {
		String str = ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC;
		for (int i = 0; i < arr.size(); i++) {
			str += arr.get(i);
			if (i != arr.size() - 1)
				str += "\n" + ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC;
		}
		this.config.set("Metaboard.Lore", str);
		this.saveData();
	}

	public void setMetaEnchants(Map<Enchantment, Integer> map) {
		this.config.set("Metaboard.Enchants", map.toString());
		this.saveData();
	}
}
