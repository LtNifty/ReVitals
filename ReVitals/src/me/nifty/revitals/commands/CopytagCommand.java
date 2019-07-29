package me.nifty.revitals.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.nifty.revitals.Main;
import me.nifty.revitals.PlayerDataHandler;

public class CopytagCommand implements CommandExecutor {

	public CopytagCommand(Main plugin) {
		plugin.getCommand("copytag").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.DARK_RED + "Only players may execute this command!");
			return true;
		}

		Player p = (Player) sender;
		ItemStack hand = p.getInventory().getItemInMainHand();
		String usage = "\n§cUsage: §6/" + label + " §e<§6name§e|§6lore§e|§6enchants§e|§6all§e>";

		if (p.hasPermission("revitals.metaboard")) {
			if (args.length != 0) {
				if (args[0].equals("name") || args[0].equals("lore") || args[0].equals("enchants") || args[0].equals("all")) {
					if (hand.getType() != Material.AIR) {
						ItemMeta handMeta = hand.getItemMeta();
						PlayerDataHandler pd = new PlayerDataHandler(p);
						switch (args[0]) {
						case "name":
							if (handMeta.hasDisplayName())
								pd.setMetaItemName(handMeta.getDisplayName());
							else {
								p.sendMessage(ChatColor.RED + "There's no name to copy.");
								return false;
							}
							break;
						case "lore":
							if (handMeta.hasLore())
								pd.setMetaLore(handMeta.getLore());
							else {
								p.sendMessage(ChatColor.RED + "There's no lore to copy.");
								return false;
							}
							break;
						case "enchants":
							if (p.hasPermission("revitals.*")) {
								if (handMeta.hasEnchants())
									pd.setMetaEnchants(handMeta.getEnchants());
								else {
									p.sendMessage(ChatColor.RED + "There's no enchants to copy.");
									return false;
								}
							} else {
								p.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
								return false;
							}
							break;
						case "all":
							if (p.hasPermission("revitals.*"))
								if (CraftItemStack.asNMSCopy(hand).getTag() != null) {
									if (handMeta.hasDisplayName())
										pd.setMetaItemName(handMeta.getDisplayName());
									if (handMeta.hasLore())
										pd.setMetaLore(handMeta.getLore());
									if (handMeta.hasEnchants())
										pd.setMetaEnchants(handMeta.getEnchants());
								}
								else {
									p.sendMessage(ChatColor.RED + "There's no tags to copy.");
									return false;
								}
							else {
								p.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
								return false;
							}
							break;
						}
						p.sendMessage(ChatColor.GREEN + "Copied to your metaboard!\nUse " + ChatColor.GOLD + "/metaboard" + ChatColor.GREEN + " to access it!");
					} else
						p.sendMessage(ChatColor.RED + "You must be holding an item.");
				} else
					p.sendMessage(ChatColor.RED + "Invalid argument." + usage);
			} else
				p.sendMessage(ChatColor.RED + "Check argument count." + usage);
		} else
			p.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
		return false;
	}
}
