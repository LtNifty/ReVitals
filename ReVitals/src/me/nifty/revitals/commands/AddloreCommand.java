package me.nifty.revitals.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.nifty.revitals.Main;

public class AddloreCommand implements CommandExecutor {

	public AddloreCommand(Main plugin) {
		plugin.getCommand("addlore").setExecutor(this);
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
			if (hand.getType() != Material.AIR) {
				ItemStack temp = hand;
				ItemMeta tempMeta = temp.getItemMeta();
				List<String> lore = tempMeta.getLore();
				int place;
				
				if (lore == null) {
					lore = new ArrayList<String>();
				}
				
				lore.add("");
				place = lore.size() - 1;
				for (int i = 0; i < args.length; i++)
					if (args[i].equalsIgnoreCase("\\n"))
						lore.add("");
				
				for (int i = 0; i < args.length; i++) {
					if (args[i].equalsIgnoreCase("\\n")) {
						i++;
						place++;
					}
					lore.set(place, lore.get(place) + ChatColor.translateAlternateColorCodes('&', args[i]));
					if (i + 1 != args.length)
						lore.set(place, lore.get(place) + " ");
				}
				
				tempMeta.setLore(lore);
				temp.setItemMeta(tempMeta);
				
				p.getInventory().setItemInMainHand(temp);
				p.sendMessage("§aSuccess!");
			} else
				p.sendMessage("§cYou must have an item in your hand!");
		} else
			p.sendMessage("§4You do not have access to that command.");
		
		return false;
	}
}
