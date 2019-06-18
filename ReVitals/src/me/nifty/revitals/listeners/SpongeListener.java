package me.nifty.revitals.listeners;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.nifty.revitals.Main;

public class SpongeListener implements Listener {
	
	private ArrayList<UUID> noBounce = new ArrayList<UUID>();

	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent e) {
		UUID pID = e.getPlayer().getUniqueId();
		if (!(e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SPONGE))
			this.noBounce.remove(pID);
		else {
			if (e.getPlayer().isSneaking())
				this.noBounce.add(pID);
			else if (!(this.noBounce.contains(pID))) {
				e.getPlayer().setVelocity(e.getPlayer().getVelocity().setY(2));
				if (!Main.safeFall.contains(pID))
					Main.safeFall.add(pID);
			}
		}
	}
}
