package me.nifty.revitals;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Sidebar {

	private Main plugin = Main.getPlugin(Main.class);

	public Sidebar() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					ScoreboardManager m = Bukkit.getScoreboardManager();
					Scoreboard b = m.getNewScoreboard();

					Objective o = b.registerNewObjective("Player Info","","");
					o.setDisplaySlot(DisplaySlot.SIDEBAR);
					o.setDisplayName(ChatColor.BOLD + p.getDisplayName());

					// rank lines
					Score rankT = o.getScore(ChatColor.GOLD + "Rank");
					rankT.setScore(10);
					Score rank = o.getScore(Main.getSuperGroupTag(p, false) + ChatColor.WHITE + Main.getGroup(p, false));
					rank.setScore(9);

					// spacer
					Score newLine = o.getScore("  ");
					newLine.setScore(8);

					// balance lines
					Score balT = o.getScore(ChatColor.GREEN + "Balance");
					balT.setScore(7);
					Score balance = o.getScore(String.format("$%,.2f", Main.economy.getBalance(p)));
					balance.setScore(6);

					// spacer
					Score newLine1 = o.getScore(" ");
					newLine1.setScore(5);

					// worldtime lines
					Score timeT = o.getScore(ChatColor.AQUA + "World Time");
					timeT.setScore(4);
					Score time = o.getScore(WorldTime.time("6d7"));
					time.setScore(3);

					// spacer
					Score newLine3 = o.getScore("   ");
					newLine3.setScore(2);

					Score websiteT = o.getScore(ChatColor.RED + "Website");
					websiteT.setScore(1);

					Score website = o.getScore("7d6.enjin.com");
					website.setScore(0);

					p.setScoreboard(b);
				}
			}
		}, 0, 20);
	}
}
