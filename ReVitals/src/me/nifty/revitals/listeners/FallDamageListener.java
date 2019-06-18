package me.nifty.revitals.listeners;

import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.nifty.revitals.Main;

public class FallDamageListener implements Listener {

	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			Material landOn = p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType();
			if (e.getCause() == DamageCause.FALL) {
				UUID pID = p.getUniqueId();
				if (landOn == Material.SPONGE || landOn == Material.EMERALD_BLOCK) e.setCancelled(true);
				else if (Main.safeFall.contains(pID)) {
					Main.safeFall.remove(p.getUniqueId());
					e.setCancelled(true);
				}
				else if (p.getGameMode() != GameMode.CREATIVE && p.isFlying()) {
					p.setFlying(false);
					return;
				}
			}
		}
	}
}
