package me.nifty.revitals.commands;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.nifty.revitals.Main;

public class CleartagCommand implements CommandExecutor {

	public CleartagCommand(Main plugin) {
		plugin.getCommand("cleartag").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command!");
			return true;
		}

		Player p = (Player) sender;
		ItemStack hand = p.getInventory().getItemInMainHand();

		if (p.hasPermission("revitals.clipboard")) {
			if (args.length != 0) {
				if ((args[0].equals("name") && args.length == 1) || args[0].equals("lore") || args[0].equals("enchants") || (args[0].equals("all") && args.length == 1)) {
					if (hand.getType() != Material.AIR) {
						ItemMeta handMeta = hand.getItemMeta();
						List<String> lore = handMeta.getLore();
						switch (args[0]) {
						case "name":
							if (handMeta.hasDisplayName())
								handMeta.setDisplayName(null);
							else {
								p.sendMessage("§cThere's no name to remove.");
								return false;
							}
							break;
						case "lore":
							if (lore != null) {
								if (args.length != 1) {
									for (int i = 1; i < args.length; i++)
										try {
											lore.set(Integer.parseInt(args[i]) - 1, null);
										} catch (Exception e) {
											p.sendMessage("§cInvalid value (\'" + args[i] + "\') for §e[§6line#§e]§c (Does not match an existing line #)." 
													+ "\n§cUsage: §6/cleartags §e<§6name§e|§6lore§e|§6enchants§e|§6all§e> §e[§6line#'s§e/§6enchantnames§e]");
											return false;
										}
									lore.removeAll(Collections.singleton(null));
									handMeta.setLore(lore);
								} else
									handMeta.setLore(null);
							} else {
								p.sendMessage("§cThere's no lore to remove."); 
								return false;
							}
							break;
						case "enchants":
							if (p.hasPermission("revitals.*")) {
								if (handMeta.hasEnchants()) {
									if (args.length != 1);
//										for (int i = 1; i < args.length; i++)
//											handMeta.removeEnchant(Enchantment.getByName(args[i]));
									else
										for (Entry<Enchantment, Integer> entry : handMeta.getEnchants().entrySet())
											handMeta.removeEnchant(entry.getKey());
								} else {
									p.sendMessage("§cThere's no enchantments to remove.");
									return false;
								}
							} else {
								p.sendMessage("§4You do not have access to that command.");
								return false;
							}
							break;
						case "all":
							if (p.hasPermission("revitals.*")) {
								if (CraftItemStack.asNMSCopy(hand).getTag() != null)
									handMeta = null;
								else {
									p.sendMessage("§cThere's no tags to remove.");
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
							+ "\n§cUsage: §6/" + label + " §e<§6name§e|§6lore§e|§6enchants§e|§6all§e> §e[§6line#§e/§6enchantname§e]");
			} else
				p.sendMessage("§cCheck argument count."
						+ "\n§cUsage: §6/" + label + " §e<§6name§e|§6lore§e|§6enchants§e|§6all§e> §e[§6line#§e/§6enchantname§e]");
		} else
			p.sendMessage("§4You do not have access to that command.");
		return false;
	}
}
