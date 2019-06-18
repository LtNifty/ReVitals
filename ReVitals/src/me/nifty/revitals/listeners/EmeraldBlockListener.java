package me.nifty.revitals.listeners;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EmeraldBlockListener implements Listener {
	
	private ArrayList<UUID> emmyBlock = new ArrayList<UUID>();
	
	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent e) {
		boolean isBlockEmmy = e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.EMERALD_BLOCK;
		Player p = e.getPlayer();
		if (isBlockEmmy && !this.emmyBlock.contains(p.getUniqueId())) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3), true);
			this.emmyBlock.add(p.getUniqueId());
		}
		else if (!isBlockEmmy && this.emmyBlock.contains(p.getUniqueId())) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 15, 3), true);
			this.emmyBlock.remove(p.getUniqueId());
		}
	}	
}
