package me.nifty.revitals.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.nifty.revitals.Main;

public class ClearnameCommand implements CommandExecutor {

	public ClearnameCommand(Main plugin) {
		plugin.getCommand("clearname").setExecutor(this);
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
				if (args.length == 0) {
					if (hand.getItemMeta().hasDisplayName()) {
						ItemMeta tempMeta = hand.getItemMeta();

						tempMeta.setDisplayName(null);
						hand.setItemMeta(tempMeta);

						p.sendMessage("§aSuccess!");
					} else
						p.sendMessage("§cThere's no name to remove.");
				} else
					p.sendMessage("§cToo many arguments."
							+ "\n§cUsage: §6/clearname");
			} else
				p.sendMessage("§cYou must have an item in your hand.");
		} else
			p.sendMessage("§4You do not have access to that command.");

		return false;
	}
}
