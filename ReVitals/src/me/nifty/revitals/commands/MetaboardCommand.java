package me.nifty.revitals.commands;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.nifty.revitals.Main;
import me.nifty.revitals.PlayerDataHandler;

public class MetaboardCommand implements CommandExecutor {

	public MetaboardCommand(Main plugin) {
		plugin.getCommand("metaboard").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.DARK_RED + "Only players may execute this command!");
			return true;
		}

		Player p = (Player) sender;
		String usage = "\n§cUsage: §6/" + label;

		if (p.hasPermission("revitals.metaboard")) {
			if (args.length == 0) {
				PlayerDataHandler pd = new PlayerDataHandler(p);
				String name = ChatColor.ITALIC + pd.getConfig().getString("Metaboard.Item Name"), 
						lore =  pd.getConfig().getString("Metaboard.Lore"),
						enchants =  pd.getConfig().getString("Metaboard.Enchants");
				// chatcolor.italic counts as 2 chars
				if (name.length() == 2)
					name = "None!";
				if (lore.length() == 0)
					lore = "None!";
				if (enchants.length() == 0)
					enchants = "None!";

				p.sendMessage("§d<=-=-=-=-=§e§lMetaboard§r§d=-=-=-=-=>"
						+ "\n" + ChatColor.RESET + ChatColor.AQUA + "Display Name: " + ChatColor.RESET + name
						+ "\n" + ChatColor.RESET + ChatColor.AQUA + "Lore: " + ChatColor.RESET + "\n" + lore
						+ "\n" + ChatColor.RESET + ChatColor.AQUA + "Enchants: " + ChatColor.RESET + "\n" + WordUtils.capitalize(enchants.replaceAll("_", " "))
						+ "\n§r§d<=-=-=-=-=-=-=-=-=-=-=-=-=-=>");
			} else
				p.sendMessage(ChatColor.RED + "Check argument count." + usage);
		} else
			p.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
		return false;
	}
}
