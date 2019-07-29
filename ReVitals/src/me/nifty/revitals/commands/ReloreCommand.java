package me.nifty.revitals.commands;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.nifty.revitals.Main;

public class ReloreCommand implements CommandExecutor {

	public ReloreCommand(Main plugin) {
		plugin.getCommand("relore").setExecutor(this);
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

		if (p.hasPermission("revitals.lore")) {
			if (args.length != 0) {
				if (hand.getType() != Material.AIR) {
					ItemMeta handMeta = hand.getItemMeta();
					List<String> lore;
					String str = "";

					for (String s : args) {
						str += ChatColor.translateAlternateColorCodes('&', s);
						if (!s.equalsIgnoreCase("\\n"))
							str += " ";
					}
					str = str.trim();
					lore = Arrays.asList(str.split(Pattern.quote("\\n")));
					if (!(lore.size() > 10)) {
						for (String s : lore)
							if (ChatColor.stripColor(s).length() > 30) {
								p.sendMessage(ChatColor.RED + "Too many characters (\"" + s.substring(0, 7) + ChatColor.RED + "...\" exceeds 30 char limit).");
								return false;
							}
					} else {
						p.sendMessage(ChatColor.RED + "Too many lines of lore (Exceeds 10 line limit).");
						return false;
					}
					handMeta.setLore(lore);
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
