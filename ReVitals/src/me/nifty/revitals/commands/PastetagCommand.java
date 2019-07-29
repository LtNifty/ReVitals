package me.nifty.revitals.commands;

import java.util.Arrays;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.nifty.revitals.Main;
import me.nifty.revitals.PlayerDataHandler;

public class PastetagCommand implements CommandExecutor {

	public PastetagCommand(Main plugin) {
		plugin.getCommand("pastetag").setExecutor(this);
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
						String name = pd.getConfig().getString("Metaboard.Item Name"), 
								lore = pd.getConfig().getString("Metaboard.Lore"), 
								enchants = pd.getConfig().getString("Metaboard.Enchants");
						switch (args[0]) {
						case "name":
							if (name.length() != 0)
								handMeta.setDisplayName(name);
							else {
								p.sendMessage(ChatColor.RED + "There's no name to paste.");
								return false;
							}
							break;
						case "lore":
							if (lore.length() != 0)
								handMeta.setLore(Arrays.asList(lore.split(Pattern.quote("\n"))));
							else {
								p.sendMessage(ChatColor.RED + "There's no lore to paste.");
								return false;
							}
							break;
						case "enchants":
							if (p.hasPermission("revitals.*")) {
								if (enchants.length() != 0)
									handMeta = this.pasteEnchants(enchants, handMeta);
								else {
									p.sendMessage(ChatColor.RED + "There's no enchants to paste.");
									return false;
								}
							} else {
								p.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
								return false;
							}
							break;
						case "all":
							if (p.hasPermission("revitals.*")) {
								if (name.length() != 0 || lore.length() != 0 || enchants.length() != 0) {
									if (name.length() != 0)
										handMeta.setDisplayName(name);
									if (lore.length() != 0)
										handMeta.setLore(Arrays.asList(lore.split(Pattern.quote("\n"))));
									if (enchants.length() != 0)
										handMeta = this.pasteEnchants(enchants, handMeta);

								} else {
									p.sendMessage(ChatColor.RED + "There's no tags to paste.");
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

	public ItemMeta pasteEnchants(String str, ItemMeta meta) {
		for (Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet())
			meta.removeEnchant(entry.getKey());
		for (String s : Arrays.asList(str.split(Pattern.quote("\n"))))
			meta.addEnchant(EnchantmentWrapper.getByKey(NamespacedKey.minecraft(s.substring(0, s.indexOf('[') - 1).toLowerCase())), Integer.parseInt(s.substring(s.indexOf('[') + 1, s.indexOf(']'))), false);
		return meta;
	}
}
