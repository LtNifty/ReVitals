package me.nifty.revitals.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.nifty.revitals.Main;

public class RenameCommand implements CommandExecutor {

	public RenameCommand(Main plugin) {
		plugin.getCommand("rename").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.DARK_RED + "Only players may execute this command!");
			return true;
		}

		Player p = (Player) sender;
		ItemStack hand = p.getInventory().getItemInMainHand();
		String usage = "\n§cUsage: §6/" + label + " §e<§6text§e>";

		if (p.hasPermission("revitals.rename")) {
			if (args.length != 0) {
				if (hand.getType() != Material.AIR) {
					ItemMeta handMeta = hand.getItemMeta();
					String str = "";
					for (int i = 0; i < args.length; i++)
						str += ChatColor.translateAlternateColorCodes('&', args[i]).concat(" ");
					str = str.trim();
					if (ChatColor.stripColor(str).length() > 30) {
						p.sendMessage(ChatColor.RED + "Too many characters (Exceeds 30 char limit).");
						return false;
					}
					handMeta.setDisplayName(str);
					hand.setItemMeta(handMeta);
					p.sendMessage(ChatColor.GREEN + "Success!");
				} else
					p.sendMessage(ChatColor.RED + "You must be holding an item.");
			} else
				p.sendMessage(ChatColor.RED + "Check argument count." + usage);
		} else
			p.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
		return false;
	}
}
