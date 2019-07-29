package me.nifty.revitals.listeners;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.IEssentials;

import me.nifty.revitals.Main;
import me.nifty.revitals.PlayerDataHandler;

public class PvPListener implements Listener {

	private Main plugin = Main.getPlugin(Main.class);


	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player enemy = (Player) e.getDamager();
			PlayerDataHandler pd = new PlayerDataHandler(enemy);
			int taskID = 0;

			if (!pd.getConfig().getBoolean("Updateables.Skulled")) {
				pd.set("Updateables.Skulled", true);
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
						pd.set("Updateables.Skulled", false);
						enemy.sendMessage("§2§lx-x-x-x-x-x-x-x-x-x-x-x-x-x"
								+ "\nYou are §a§lunskulled§r§2§l."
								+ "§2§l\nx-x-x-x-x-x-x-x-x-x-x-x-x-x");
					}

				}, 200).getTaskId();
			} else {
				Bukkit.getServer().getScheduler().cancelTask(taskID);
				taskID = Bukkit.getServer().getScheduler().runTaskLater(this.plugin, new Runnable() {

					@Override
					public void run() {
						pd.set("Updateables.Skulled", false);
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
		Player player = e.getEntity();
		ArrayList<ItemStack> keepItems = new ArrayList<ItemStack>();
		Essentials ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
		BigDecimal max = BigDecimal.ZERO;
		ItemStack maxItem = null;
		
		for (ItemStack item : player.getInventory()) {
			if (item != null  && !keepItems.contains(item) && ess.getWorth().getPrice((IEssentials)ess, item).compareTo(max) == -1) {
				maxItem = item;
				max = ess.getWorth().getPrice(ess, item);
			}
		}
		
		player.sendMessage("Your most expensive item was: " + maxItem + " with a price of " + max);
	}
}
