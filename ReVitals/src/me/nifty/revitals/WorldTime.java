package me.nifty.revitals;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WorldTime {

	private Main plugin = Main.getPlugin(Main.class);
	private String timeMsg = ChatColor.GREEN + "[Worldtime] " + ChatColor.AQUA;

	public WorldTime() {
		new BukkitRunnable() {
			@Override
			public void run() {
				long worldTime = Bukkit.getServer().getWorld("6d7").getTime();

				if (Main.hasPlayers()) {
					if (worldTime == 0)
						for (Player p : Bukkit.getOnlinePlayers()) {
							p.sendMessage(timeMsg + "A new day has started.");
							dailyReward();
						}
					else if (worldTime == 12000)
						for (Player p : Bukkit.getOnlinePlayers())
							p.sendMessage(timeMsg + "Darkness begins to fall.");
					else if (worldTime == 13000)
						for (Player p : Bukkit.getOnlinePlayers())
							p.sendMessage(timeMsg + "Night time has fallen.");
					else if (worldTime == 23000)
						for (Player p : Bukkit.getOnlinePlayers())
							p.sendMessage(timeMsg + "The sun's light starts to become visible.");
				}
			}
		}.runTaskTimerAsynchronously(this.plugin, 0, 2);
	}
	private void dailyReward() {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			Main.economy.depositPlayer(p, 500);
			p.sendMessage(ChatColor.GREEN + "You have been awarded a daily salary of $500.");
		}
	}

	public static String time(String world) {
		long time = Bukkit.getServer().getWorld(world).getFullTime();
		int hours = (int)((time/1000+8)%24);
		int minutes = (int)(60*(time%1000)/1000);
		return String.format("%02d:%02d", hours, minutes);
	}
}
