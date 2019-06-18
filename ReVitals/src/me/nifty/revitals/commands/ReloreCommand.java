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
			sender.sendMessage("Only players may execute this command!");
			return true;
		}

		Player p = (Player) sender;
		ItemStack hand = p.getInventory().getItemInMainHand();

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
								p.sendMessage("§cToo many characters (\"" + s.substring(0, 7) + "§r§c...\" exceeds 30 char limit).");
								return false;
							}
					} else {
						p.sendMessage("§cToo many lines of lore (Exceeds 10 line limit).");
						return false;
					}
					handMeta.setLore(lore);
					hand.setItemMeta(handMeta);
					p.sendMessage("§aSuccess!");
				} else
					p.sendMessage("§cYou must have an item in your hand.");
			} else
				p.sendMessage("§cMissing argument for §e<§6text§e>§c." +
						"\n§cUsage: §6/" + label + " §e<§6text§e>");

		} else
			p.sendMessage("§4You do not have access to that command.");

		return false;
	}
}
