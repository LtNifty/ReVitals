package me.nifty.revitals.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.nifty.revitals.Main;

public class EmeraldListener implements Listener {

	private Main plugin;
	boolean valid = true;

	public EmeraldListener(Main p) {
		this.plugin = p;
	}

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Action a = e.getAction();
		ItemStack held = p.getInventory().getItemInMainHand();

		if (held.getType().equals(Material.EMERALD) && (a.equals(Action.RIGHT_CLICK_AIR) || a.equals(Action.RIGHT_CLICK_BLOCK))) {
			if (Main.emmyUsers.contains(p.getUniqueId())) {
				Main.emmyUsers.remove(p.getUniqueId());
				p.removePotionEffect(PotionEffectType.SPEED);
				p.sendMessage("§7§oThe wind ceases...");
				valid = false;
				return;
			}
			else {
				Main.emmyUsers.add(p.getUniqueId());
				p.sendMessage("§7§oHasten your way on wind-touched heels!");
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 3), true);
				held.setAmount(held.getAmount() - 1);
				valid = true;
				this.removeEmmy(p, held);
			}
		}
	}

	public void removeEmmy(Player p, ItemStack held) {
		Bukkit.getServer().getScheduler().runTaskLater(this.plugin, new Runnable() {

			@Override
			public void run() {
				if (valid) {
					if (p.getInventory().contains(Material.EMERALD) && Main.emmyUsers.contains(p.getUniqueId())) {
						held.setAmount(held.getAmount() - 1);
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 3), true);
						removeEmmy(p, held);
					}
					else if (Main.emmyUsers.contains(p.getUniqueId()) == false){
						p.sendMessage("§7§oOh dear! You've run out of emeralds!");
					}
				}
			}
		}, 40);
	}
}
