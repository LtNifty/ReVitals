package me.nifty.revitals.commands;

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
			sender.sendMessage("Only players may execute this command!");
			return true;
		}

		Player p = (Player) sender;
		ItemStack hand = p.getInventory().getItemInMainHand();

		if (p.hasPermission("revitals.metaboard")) {
			if (args.length != 0) {
				if (args[0].equals("name") || args[0].equals("lore") || args[0].equals("enchants") || args[0].equals("all")) {
					if (hand.getType() != Material.AIR) {
						ItemMeta handMeta = hand.getItemMeta();
						PlayerDataHandler pd = Main.onlinePD.get(p.getUniqueId());
						switch (args[0]) {
						case "name":
							if (handMeta.hasDisplayName())
								pd.setMetaItemName(handMeta.getDisplayName());
							else {
								p.sendMessage("§cThere's no name to copy.");
								return false;
							}
							break;
						case "lore":
							if (handMeta.hasLore())
								pd.setMetaLore(handMeta.getLore());
							else {
								p.sendMessage("§cThere's no lore to copy.");
								return false;
							}
							break;
						case "enchants":
							if (p.hasPermission("revitals.*")) {
								if (handMeta.hasEnchants())
									pd.setMetaEnchants(handMeta.getEnchants());
								else {
									p.sendMessage("§cThere's no enchantments to copy.");
									return false;
								}
							} else {
								p.sendMessage("§4You do not have access to that command.");
								return false;
							}
							break;
						case "all":
							if (p.hasPermission("revitals.*"))
								if (CraftItemStack.asNMSCopy(hand).getTag() != null) {
									pd.setMetaItemName(handMeta.getDisplayName());
									pd.setMetaLore(handMeta.getLore());
									pd.setMetaEnchants(handMeta.getEnchants());
								}
								else {
									p.sendMessage("§cThere's no tags to copy.");
									return false;
								}
							else {
								p.sendMessage("§4You do not have access to that command.");
								return false;
							}
							break;
						}
						p.sendMessage("§aCopied to your metaboard!"
								+ "\n§aUse §6/metaboard §ato access it!");
					} else
						p.sendMessage("§cYou must have an item in your hand.");
				} else
					p.sendMessage("§cInvalid argument."
							+ "\n§cUsage: §6/" + label + " §e<§6name§e|§6lore§e|§6enchants§e|§6all§e>");
			} else
				p.sendMessage("§cCheck argument count."
						+ "\n§cUsage: §6/" + label + " §e<§6name§e|§6lore§e|§6enchants§e|§6all§e>");
		} else
			p.sendMessage("§4You do not have access to that command.");
		return false;
	}
}
