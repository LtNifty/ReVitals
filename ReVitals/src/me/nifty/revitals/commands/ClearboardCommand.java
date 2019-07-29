package me.nifty.revitals.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.nifty.revitals.Main;
import me.nifty.revitals.PlayerDataHandler;

public class ClearboardCommand implements CommandExecutor {

	public ClearboardCommand(Main plugin) {
		plugin.getCommand("clearboard").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.DARK_RED + "Only players may execute this command!");
			return true;
		}

		Player p = (Player) sender;
		String usage = "\n§cUsage: §6/" + label + " §e<§6name§e|§6lore§e|§6enchants§e|§6all§e>";

		if (p.hasPermission("revitals.metaboard")) {
			if (args.length == 1) {
				if (args[0].equals("name") || args[0].equals("lore") || args[0].equals("enchants") || args[0].equals("all")) {
					PlayerDataHandler pd = new PlayerDataHandler(p);
					String name = pd.getConfig().getString("Metaboard.Item Name"), 
							lore =  pd.getConfig().getString("Metaboard.Lore"),
							enchants =  pd.getConfig().getString("Metaboard.Enchants");
					switch (args[0]) {
					case "name":
						if (name.length() != 0)
							pd.setMetaItemName(null);
						else {
							p.sendMessage(ChatColor.RED + "There's no name to clear.");
							return false;
						}
						break;
					case "lore":
						if (lore.length() != 0)
							pd.setMetaLore(null);
						else {
							p.sendMessage(ChatColor.RED + "There's no lore to clear.");
							return false;
						}
						break;
					case "enchants":
						if (p.hasPermission("revitals.*")) {
							if (enchants.length() != 0)
								pd.setMetaEnchants(null);
							else {
								p.sendMessage(ChatColor.RED + "There's no enchants to clear.");
								return false;
							}
						} else {
							p.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
							return false;
						}
						break;
					case "all":
						if (p.hasPermission("revitals.*")) {
							if (name.length() != 0 || lore.length() != 0 || enchants.length() != 0) {
								pd.setMetaItemName(null);
								pd.setMetaLore(null);
								pd.setMetaEnchants(null);
							} else {
								p.sendMessage(ChatColor.RED + "There's no tags to clear.");
								return false;
							}
						} else {
							p.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
							return false;
						}
						break;
					}
					p.sendMessage(ChatColor.GREEN + "Success!");
				} else
					p.sendMessage(ChatColor.RED + "Invalid argument." + usage);
			} else
				p.sendMessage(ChatColor.RED + "Check argument count." + usage);
		} else
			p.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
		return false;
	}
}
