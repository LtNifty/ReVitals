package me.nifty.revitals.listeners;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.earth2me.essentials.Essentials;

import me.nifty.revitals.Main;

public class PvPListener implements Listener {

	public static ArrayList<UUID> skulled = new ArrayList<UUID>();
	private Main plugin;
	private Essentials ess;
	private int taskID;

	public PvPListener(Main plugin, Essentials ess) {
		this.plugin = plugin;	
		this.ess = ess;
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
//			Player p = (Player) e.getEntity();
			Player enemy = (Player) e.getDamager();

			if (!skulled.contains(enemy.getUniqueId())) {
				skulled.add(enemy.getUniqueId());
				enemy.sendMessage("§4§lx-x-x-x-x-x-x-x-x-x-x-x-x-x"
						+ "\nYou are §c§lskulled§r§4§l."
						+ "\n§eIf you die, you can no longer"
						+ "\nkeep your three most expensive"
						+ "\nitems. All of your items will be"
						+ "\ndropped upon death. Be careful!"
						+ "\n\n§7To turn off this tooltip: §e/chrisisLame"
						+ "\n§4§lx-x-x-x-x-x-x-x-x-x-x-x-x-x");
				taskID = Bukkit.getServer().getScheduler().runTaskLater(this.plugin, new Runnable() {

					@Override
					public void run() {
						skulled.remove(enemy.getUniqueId());
						enemy.sendMessage("§2§lx-x-x-x-x-x-x-x-x-x-x-x-x-x"
								+ "\nYou are §a§lunskulled§r§2§l."
								+ "§2§l\nx-x-x-x-x-x-x-x-x-x-x-x-x-x");
					}

				}, 200).getTaskId();
			}
			else {
				Bukkit.getServer().getScheduler().cancelTask(taskID);
				taskID = Bukkit.getServer().getScheduler().runTaskLater(this.plugin, new Runnable() {

					@Override
					public void run() {
						skulled.remove(enemy.getUniqueId());
						enemy.sendMessage("§2§lx-x-x-x-x-x-x-x-x-x-x-x-x-x"
								+ "\nYou are §a§lunskulled§r§2§l."
								+ "§2§l\nx-x-x-x-x-x-x-x-x-x-x-x-x-x");
					}

				}, 200).getTaskId();
			}
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		ArrayList<ItemStack> keepItems = new ArrayList<ItemStack>();
		BigDecimal max = BigDecimal.ZERO;
		ItemStack maxItem = null;

			for (ItemStack item : p.getInventory()) {
				p.sendMessage("Found " + item + "! Price: " + ess.getWorth().getPrice(ess, item));
				if (keepItems.contains(item) == false && ess.getWorth().getPrice(ess, item).compareTo(max) > 1) {
					p.sendMessage("new max found; PRICE: " + ess.getWorth().getPrice(ess, item));
					maxItem = item;
					max = ess.getWorth().getPrice(ess, item);
				}
			}
			p.sendMessage(maxItem + " had the highest price!");
			keepItems.add(maxItem);
		p.sendMessage("Your most expensive items were: " + keepItems);
	}
}
