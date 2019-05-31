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

public class CleartagsCommand implements CommandExecutor {

	public CleartagsCommand(Main plugin) {
		plugin.getCommand("cleartags").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command!");
			return true;
		}

		Player p = (Player) sender;
		ItemStack hand = p.getInventory().getItemInMainHand();

		if (p.hasPermission("revitals.admin")) {
			if (hand.getType() != Material.AIR) {
				if (args.length > 0) {
					if (CraftItemStack.asNMSCopy(hand).getTag() != null) {
						p.sendMessage(new ItemStack(hand.getType()).getItemMeta().toString());
						p.sendMessage(hand.getItemMeta().toString());
						ItemStack temp = new ItemStack(hand.getType());
						ItemMeta tempMeta = temp.getItemMeta();

						temp.setItemMeta(tempMeta);
						temp.setAmount(hand.getAmount());

						p.getInventory().setItemInMainHand(temp);
						p.sendMessage("§aSuccess!");
					} else
						p.sendMessage("§cThere's no tags to remove.");
				} else
					p.sendMessage("§cToo many arguments."
							+ "\n§cUsage: §6/cleartags");
			} else
				p.sendMessage("§cYou must have an item in your hand.");
		} else
			p.sendMessage("§4You do not have access to that command.");

		return false;
	}
}
