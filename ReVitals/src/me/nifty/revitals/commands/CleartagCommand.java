package me.nifty.revitals.commands;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
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
			sender.sendMessage(ChatColor.DARK_RED + "Only players may execute this command!");
			return true;
		}

		Player p = (Player) sender;
		ItemStack hand = p.getInventory().getItemInMainHand();
		String usage = "\n§cUsage: §6/" + label + " §e<§6name§e|§6lore§e|§6enchants§e|§6all§e> §e[§6line#§e/§6enchantname§e]";

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
								p.sendMessage(ChatColor.RED + "There's no name to remove.");
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
											p.sendMessage(ChatColor.RED + "Invalid value (\'" + args[i] + "') for §e[§6line#§e]§c (Does not match an existing line #)." + usage);
											return false;
										}
									lore.removeAll(Collections.singleton(null));
									handMeta.setLore(lore);
								} else
									handMeta.setLore(null);
							} else {
								p.sendMessage(ChatColor.RED + "There's no lore to remove."); 
								return false;
							}
							break;
						case "enchants":
							if (p.hasPermission("revitals.*")) {
								if (handMeta.hasEnchants()) {
									if (args.length != 1)
										for (int i = 1; i < args.length; i++) {
											try {
												if (!handMeta.removeEnchant(EnchantmentWrapper.getByKey(NamespacedKey.minecraft(args[i])))) {
													p.sendMessage(ChatColor.RED + args[i] + " is not enchanted on the item!" + usage);
													return false;
												}
											} catch (IllegalArgumentException e) {
												p.sendMessage(ChatColor.RED + "Invalid string (\"" + args[i] + "\") for enchantment (Does not match a valid enchantment name)." + usage);
												return false;
											}
										}
									else
										for (Entry<Enchantment, Integer> entry : handMeta.getEnchants().entrySet())
											handMeta.removeEnchant(entry.getKey());
								} else {
									p.sendMessage(ChatColor.RED + "There's no enchants to remove.");
									return false;
								}
							} else {
								p.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
								return false;
							}
							break;
						case "all":
							if (p.hasPermission("revitals.*")) {
								if (CraftItemStack.asNMSCopy(hand).getTag() != null) {
									handMeta.setDisplayName(null);
									handMeta.setLore(null);
									for (Entry<Enchantment, Integer> entry : handMeta.getEnchants().entrySet())
										handMeta.removeEnchant(entry.getKey());
								}
								else {
									p.sendMessage(ChatColor.RED + "There's no tags to remove.");
									return false;
								}
							} else {
								p.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
								return false;
							}
							break;
						}
						hand.setItemMeta(handMeta);
						p.sendMessage(ChatColor.GREEN + "Success!");
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
