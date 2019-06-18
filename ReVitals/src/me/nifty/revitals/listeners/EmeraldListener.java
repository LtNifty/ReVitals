package me.nifty.revitals.listeners;

import java.util.ArrayList;
import java.util.UUID;

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
	
	private ArrayList<UUID> emmyUsers = new ArrayList<UUID>();
	private Main plugin = Main.getPlugin(Main.class);

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Action a = e.getAction();
		ItemStack held = p.getInventory().getItemInMainHand();

		if (held.getType().equals(Material.EMERALD) && (a.equals(Action.RIGHT_CLICK_AIR) || a.equals(Action.RIGHT_CLICK_BLOCK))) {
			if (p.hasPermission("revitals.emmyconsume")) {
				if (!this.emmyUsers.contains(p.getUniqueId())) {
					this.emmyUsers.add(p.getUniqueId());
					p.sendMessage("§7§oHasten your way on wind-touched heels!");
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 3), true);
					held.setAmount(held.getAmount() - 1);
					this.removeEmmy(p);
				} else {
					this.emmyUsers.remove(p.getUniqueId());
					p.sendMessage("§7§oThe wind slowly dies away...");
				}
			}
		}
	}

	public void removeEmmy(Player p) {
		Bukkit.getScheduler().runTaskLater(this.plugin, new Runnable() {
			@Override
			public void run() {
				if (emmyUsers.contains(p.getUniqueId())) {
					if (p.getInventory().contains(Material.EMERALD)) {
						for (ItemStack item : p.getInventory().getContents()) {
							if (item != null && item.getType() == Material.EMERALD) {
								item.setAmount(item.getAmount() - 1);
								p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 3), true);
								removeEmmy(p);
								break;
							}
						}
					} else {
						emmyUsers.remove(p.getUniqueId());
						p.sendMessage("§7§oThe wind slowly dies away...");
					}
				}
			}
		}, 39);
	}
}
