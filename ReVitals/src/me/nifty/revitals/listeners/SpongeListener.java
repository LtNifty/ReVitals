package me.nifty.revitals.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.nifty.revitals.Main;
import me.nifty.revitals.PlayerDataHandler;

public class SpongeListener implements Listener {

	private Main plugin = Main.getPlugin(Main.class);

	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		PlayerDataHandler pd = new PlayerDataHandler(p);

		if (plugin.getConfig().getBoolean("sponge-bounce")) {
			if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SPONGE) {
				if (p.isSneaking())
					pd.set("Misc.NoBounce", true);
				else if (!(pd.getConfig().getBoolean("Misc.NoBounce"))) {
					p.setVelocity(e.getPlayer().getVelocity().setY(2));
					if (p.getGameMode() != GameMode.CREATIVE)
						pd.set("Misc.Safefall", true);
				}
			}
			else if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() != Material.SPONGE && pd.getConfig().getBoolean("Misc.NoBounce"))
				pd.set("Misc.NoBounce", false);
		}
	}
}
