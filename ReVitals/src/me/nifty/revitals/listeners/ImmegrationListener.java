package me.nifty.revitals.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.nifty.revitals.Main;
import me.nifty.revitals.PlayerDataHandler;
import me.nifty.revitals.Veteran;

public class ImmegrationListener implements Listener {
	
	private Main plugin = Main.getPlugin(Main.class);

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		PlayerDataHandler userData = new PlayerDataHandler(player);
		if (!userData.newPlayer()) {
			player.setDisplayName(userData.getConfig().getString("Display Name"));
		}
		else {
			new Veteran(player).processStatus();
			if (plugin.getConfig().getInt("citizen-time-req") != 0)
				Main.permission.playerAddGroup(null, player, "Wanderer");
			else
				Main.permission.playerAddGroup(null, player, "Citizen");
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		PlayerDataHandler userData = new PlayerDataHandler(player);
		userData.updateData();
	}
}
