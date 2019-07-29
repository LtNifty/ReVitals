package me.nifty.revitals;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

	public boolean newPlayer() {
		if (!this.file.exists()) {
			try {
				this.file.createNewFile();
				this.config.set("UUID", this.player.getUniqueId().toString());
				this.config.set("Name", this.player.getName());
				this.config.set("IP Address", this.player.getAddress().getAddress().getHostAddress());
				this.config.set("Old Playtime", 0);
				this.config.createSection("Updateables");
				this.config.set("Updateables.Display Name", this.player.getDisplayName());
				this.config.set("Updateables.Ranks", Main.permission.getPlayerGroups(this.player));
				this.config.set("Updateables.Balance", Main.economy.getBalance(this.player));
				this.config.set("Updateables.New Playtime", 0);
				this.config.set("Updateables.Skulled", false);
				this.config.createSection("Metaboard");
				this.config.set("Metaboard.Item Name", "");
				this.config.set("Metaboard.Lore", "");
				this.config.set("Metaboard.Enchants", "");
				this.config.createSection("Misc");
				this.config.set("Misc.Safefall", false);
				this.config.set("Misc.NoBounce", false);
				this.config.set("Misc.OnEmmyBlock", false);
				this.config.save(file);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
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
	
	public void updateData() {
		this.config.set("Updateables.Display Name", this.player.getDisplayName());
		this.config.set("Updateables.Ranks", Main.permission.getPlayerGroups(this.player));
		this.config.set("Updateables.Balance", Main.economy.getBalance(this.player));
		this.saveData();
	}
	
	public void set(String path, Object obj) {
		this.config.set(path, obj);
		this.saveData();
	}
	
	public void incNewPlaytime() {
		this.config.set("Updateables.New Playtime", this.config.getInt("New Playtime") + 1);
		this.saveData();
	}

	/*
	 * METABOARD METHODS BELOW
	 * DO NOT REMOVE!
	 */
	public void setMetaItemName(String str) {
		if (str == null)
			str = "";
		this.config.set("Metaboard.Item Name", str);
		this.saveData();
	}

	public void setMetaLore(List<String> arr) {
		String str = "";
		if (arr != null) {
			str = ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC;
			for (int i = 0; i < arr.size(); i++) {
				str += arr.get(i);
				if (i != arr.size() - 1)
					str += "\n" + ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC;
			}
		}
		this.config.set("Metaboard.Lore", str);
		this.saveData();
	}

	public void setMetaEnchants(Map<Enchantment, Integer> map) {
		String str = "";
		int i = 0;
		if (map != null) {
			for (Entry<Enchantment, Integer> entry : map.entrySet()) {
				str += entry.getKey().getKey().getKey() + " [" + entry.getValue() + "]";
				i++;
				if (i != map.size())
					str += "\n";
			}
		}
		this.config.set("Metaboard.Enchants", str);
		this.saveData();
	}
}
