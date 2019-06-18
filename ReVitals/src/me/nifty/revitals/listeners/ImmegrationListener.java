package me.nifty.revitals.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.nifty.revitals.Main;
import me.nifty.revitals.PlayerDataHandler;

public class ImmegrationListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		PlayerDataHandler userData = new PlayerDataHandler(p);
		userData.newPlayer();
		p.setDisplayName(userData.getConfig().getString("Display Name"));
		Main.onlinePD.put(p.getUniqueId(), userData);
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		Main.onlinePD.get(p.getUniqueId()).saveData();
		Main.onlinePD.remove(p.getUniqueId());
	}
}
