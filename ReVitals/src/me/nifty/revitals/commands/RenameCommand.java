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
			sender.sendMessage("Only players may execute this command!");
			return true;
		}

		Player p = (Player) sender;
		ItemStack hand = p.getInventory().getItemInMainHand();

		if (p.hasPermission("revitals.rename")) {
			if (hand.getType() != Material.AIR) {
				if (args.length != 0) {
					ItemMeta handMeta = hand.getItemMeta();
					String str = "";
					
					for (int i = 0; i < args.length; i++)
						str += ChatColor.translateAlternateColorCodes('&', args[i]).concat(" ");
					str.trim();
					
					handMeta.setDisplayName(str);
					hand.setItemMeta(handMeta);
					p.sendMessage("§aSuccess!");
				}
				else
					p.sendMessage("§cMissing argument for §e<§6text§e>§c." +
							"\n§cUsage: §6/rename §e<§6text§e>");
			}
			else
				p.sendMessage("§cYou must have an item in your hand!");
		} else
			p.sendMessage("§4You do not have access to that command.");

		return false;
	}
}
