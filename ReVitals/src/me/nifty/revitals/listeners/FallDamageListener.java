package me.nifty.revitals.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.nifty.revitals.PlayerDataHandler;

public class FallDamageListener implements Listener {

	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			PlayerDataHandler pd = new PlayerDataHandler(p);
			Material landOn = p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType();
			
			if (e.getCause() == DamageCause.FALL) {
				if (landOn == Material.SPONGE || landOn == Material.EMERALD_BLOCK || pd.getConfig().getBoolean("Misc.Safefall")) {
					pd.set("Misc.Safeblock", false);
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
