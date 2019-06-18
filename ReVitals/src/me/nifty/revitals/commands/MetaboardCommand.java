package me.nifty.revitals.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.nifty.revitals.Main;

public class MetaboardCommand implements CommandExecutor {

	public MetaboardCommand(Main plugin) {
		plugin.getCommand("metaboard").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command!");
			return true;
		}

		Player p = (Player) sender;

		if (p.hasPermission("revitals.metaboard")) {
			if (args.length == 0) {
				String name = Main.onlinePD.get(p.getUniqueId()).getConfig().getString("Metaboard.Item Name"), 
						lore =  Main.onlinePD.get(p.getUniqueId()).getConfig().getString("Metaboard.Lore"),
						enchants =  Main.onlinePD.get(p.getUniqueId()).getConfig().getString("Metaboard.Enchants");
				if (name.length() == 0)
					name = "None!";
				if (lore.length() == 0)
					lore = "None!";
				if (enchants.length() == 0)
					enchants = "None!";

				p.sendMessage("§d<=-=-=-=-=§e§lClipboard§r§d=-=-=-=-=>"
						+ "\n§r§bDisplay Name: §r" + name
						+ "\n§r§bLore: §r\n" + lore
						+ "\n§r§bEnchants: §r\n" + enchants
						+ "\n§r§d<=-=-=-=-=-=-=-=-=-=-=-=-=-=>");
			} else
				p.sendMessage("§cToo many arguments."
						+ "\n§cUsage: §6/" + label);
		} else
			p.sendMessage("§4You do not have access to that command.");
		return false;
	}
}
