package me.nifty.revitals.shelfed;

//import org.bukkit.Bukkit;
//import org.bukkit.Material;
//import org.bukkit.block.BlockFace;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
//import org.bukkit.event.block.Action;
//import org.bukkit.event.player.PlayerInteractEvent;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.potion.PotionEffect;
//import org.bukkit.potion.PotionEffectType;
//import me.nifty.revitals.Main;
//import me.nifty.revitals.PlayerDataHandler;
//import net.md_5.bungee.api.ChatColor;
//
public class EmeraldListener implements Listener {
//
//	private Main plugin = Main.getPlugin(Main.class);
//
//	@EventHandler
//	public void onPlayerInteractEvent(PlayerInteractEvent e) {
//		Player player = e.getPlayer();
//		PlayerDataHandler pd = new PlayerDataHandler(player);
//		String stopTask = ChatColor.GRAY + "" + ChatColor.ITALIC + "The wind slowly dies away...";
//
//		if (player.getInventory().getItemInMainHand().getType().equals(Material.EMERALD) 
//				&& (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
//			if (player.hasPermission("revitals.emmyconsume")) {
//				if (pd.getConfig().getInt("Misc.EmmyTask") == 0) {
//					player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Hasten your way on wind-touched heels!");
//					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3), true);
//					pd.set("Misc.EmmyTask", Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
//						@Override
//						public void run() {
//							if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.EMERALD_BLOCK)
//								if (player.getInventory().contains(Material.EMERALD)) {
//									for (ItemStack item : player.getInventory().getStorageContents())
//										if (item != null && item.getType() == Material.EMERALD) {
//											if (item.getAmount() == 1)
//												player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 3), true);
//											item.setAmount(item.getAmount() - 1);
//										}
//								} else {
//									Bukkit.getScheduler().cancelTask(pd.getConfig().getInt("Misc.EmmyTask"));
//									player.sendMessage(stopTask);
//									pd.set("Misc.EmmyTask", 0);
//								}
//						}
//					}, 0, 40));			
//				} else {
//					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 3), true);
//					Bukkit.getScheduler().cancelTask(pd.getConfig().getInt("Misc.EmmyTask"));
//					player.sendMessage(stopTask);
//					pd.set("Misc.EmmyTask", 0);
//				}
//			}
//		}
//	}
}