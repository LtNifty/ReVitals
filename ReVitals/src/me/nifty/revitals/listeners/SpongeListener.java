package me.nifty.revitals.listeners;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.nifty.revitals.Main;

public class SpongeListener implements Listener {

	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent e) {
		if (!(e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SPONGE)) return;
		else {
			e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9, 3), true);
			if (e.getPlayer().isSneaking()) return;
			else {
				e.getPlayer().setVelocity(e.getPlayer().getVelocity().setY(2));
				Main.safeFall.add(e.getPlayer().getUniqueId());
			}
		}
	}
}
