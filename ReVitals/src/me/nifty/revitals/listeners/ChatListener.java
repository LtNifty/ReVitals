package me.nifty.revitals.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.nifty.revitals.Main;

public class ChatListener implements Listener {

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		e.setFormat(Main.getSuperGroupTag(p, true) + Main.getGroup(p, true) + " " + p.getDisplayName() + "§r: " + Main.unicize(ChatColor.translateAlternateColorCodes('&', e.getMessage())));
	}	
}
