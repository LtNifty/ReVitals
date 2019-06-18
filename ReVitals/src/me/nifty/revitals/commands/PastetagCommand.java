package me.nifty.revitals.commands;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.nifty.revitals.Main;

public class PastetagCommand implements CommandExecutor {

	public PastetagCommand(Main plugin) {
		plugin.getCommand("pastetag").setExecutor(this);
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
						String name = Main.onlinePD.get(p.getUniqueId()).getConfig().getString("Metaboard.Item Name"), 
								lore = Main.onlinePD.get(p.getUniqueId()).getConfig().getString("Metaboard.Lore"), 
								enchants =  Main.onlinePD.get(p.getUniqueId()).getConfig().getString("Metaboard.Enchants");
						switch (args[0]) {
						case "name":
							if (name.length() != 0)
								handMeta.setDisplayName(name);
							else {
								p.sendMessage("§cThere's no name to paste.");
								return false;
							}
							break;
						case "lore":
							if (lore.length() != 0)
								handMeta.setLore(Arrays.asList(lore.split(Pattern.quote("\n"))));
							else {
								p.sendMessage("§cThere's no lore to paste.");
								return false;
							}
							break;
						case "enchants":
							if (p.hasPermission("revitals.*")) {
								if (enchants.length() != 0);
//									for (Entry<Enchantment, Integer> entry : new HashMap<Enchantment, Integer>().entrySet())
//										handMeta.addEnchant(entry.getKey(), entry.getValue(), false);
								else {
									p.sendMessage("§cThere's no enchantments to paste.");
									return false;
								}
							} else {
								p.sendMessage("§4You do not have access to that command.");
								return false;
							}
							break;
						case "all":
							if (p.hasPermission("revitals.*")) {
								if (name.length() != 0 || lore.length() != 0 || enchants.length() != 0) {
									handMeta.setDisplayName(name);
									handMeta.setLore(Arrays.asList(lore));
								}
								else {
									p.sendMessage("§cThere's no tags to paste.");
									return false;
								}
							} else {
								p.sendMessage("§4You do not have access to that command.");
								return false;
							}
							break;
						}
						hand.setItemMeta(handMeta);
						p.sendMessage("§aSuccess!");
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
