package me.nifty.revitals.commands;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.nifty.revitals.Main;

public class ClearloreCommand implements CommandExecutor {

	public ClearloreCommand(Main plugin) {
		plugin.getCommand("clearlore").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command!");
			return true;
		}

		Player p = (Player) sender;
		ItemStack hand = p.getInventory().getItemInMainHand();

		if (p.hasPermission("revitals.relore")) {
			if (hand.getType() != Material.AIR) {
				ItemMeta tempMeta = hand.getItemMeta();	
				List<String> lore = hand.getItemMeta().getLore();

				if (lore != null) {
					if (args.length != 0) {
						for (int i = 0; i < args.length; i++)
							try {
								lore.remove((int) Integer.parseInt(args[i]) - 1);
							} catch (Exception e) {
									p.sendMessage("§cInvalid value (\'" + args[i] + "\') for §e[§6line#§e]§c (Does not match a valid line #)." 
											+ "\n§cUsage: §6/clearlore §e[§6line#§e]");
								return false;
							}
						tempMeta.setLore(lore);
					}
					else
						tempMeta.setLore(null);
					hand.setItemMeta(tempMeta);
					p.sendMessage("§aSuccess!");
				}
				else
					p.sendMessage("§cThere's no lore to remove.");
			} else
				p.sendMessage("§cYou must have an item in your hand.");
		} else
			p.sendMessage("§4You do not have access to that command.");
		return false;
	} 
}
