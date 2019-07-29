package me.nifty.revitals.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.nifty.revitals.Main;
import me.nifty.revitals.PlayerDataHandler;

public class NicknameCommand implements CommandExecutor {

	public NicknameCommand(Main plugin) {
		plugin.getCommand("nickname").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.DARK_RED + "Only players may execute this command!");
			return true;
		}

		Player p = (Player) sender;
		String usage = "\n§cUsage: §6/" + label + " §e<§6text§e>";

		if (p.hasPermission("revitals.nickname")) {
			if (args.length == 1) {
				String nick = ChatColor.translateAlternateColorCodes('&', args[0]);
				if (ChatColor.stripColor(nick).length() <= 12 && ChatColor.stripColor(nick).length() != 0) {
					p.setDisplayName(nick);
					new PlayerDataHandler(p).updateData();
					p.sendMessage(ChatColor.GREEN + "Success!");
				} else
					p.sendMessage(ChatColor.RED + "Check character count (1 - 12 char range).");
			} else
				p.sendMessage(ChatColor.RED + "Check argument count (No spaces allowed)." + usage);
		} else
			p.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
		return false;
	}
}
