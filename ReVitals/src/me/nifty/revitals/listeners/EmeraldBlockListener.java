package me.nifty.revitals.listeners;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EmeraldBlockListener implements Listener {

	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent e) {
		if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.EMERALD_BLOCK)
			e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 3), true);
	}
}
