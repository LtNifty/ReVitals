package me.nifty.revitals.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.nifty.revitals.Main;

public class NicknameCommand implements CommandExecutor {

	public NicknameCommand(Main plugin) {
		plugin.getCommand("nickname").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command!");
			return true;
		}

		Player p = (Player) sender;

		if (p.hasPermission("revitals.nickname")) {
			if (args.length == 1) {
				String nick = ChatColor.translateAlternateColorCodes('&', args[0]);
				if (ChatColor.stripColor(nick).length() <= 12 && ChatColor.stripColor(nick).length() != 0) {
					p.setDisplayName(nick);
					Main.onlinePD.get(p.getUniqueId()).setDisplayName(nick);
					p.sendMessage("§aSuccess! Your nickname is now §r" + nick + "§a.");
				} else
					p.sendMessage("§cCheck character count (1 - 12 char range).");
			} else
				p.sendMessage("§cCheck argument count (No spaces allowed)."
						+ "\n§cUsage: §6/" + label + " §e<§6text§e>");
		} else
			p.sendMessage("§4You do not have access to that command.");
		return false;
	}
}
