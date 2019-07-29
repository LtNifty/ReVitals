package me.nifty.revitals.listeners;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.nifty.revitals.Main;
import me.nifty.revitals.PlayerDataHandler;

public class EmeraldBlockListener implements Listener {
	
	private Main plugin = Main.getPlugin(Main.class);

	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent e) {
		boolean isBlockEmmy = e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.EMERALD_BLOCK;
		Player p = e.getPlayer();
		PlayerDataHandler pd = new PlayerDataHandler(p);

		if (plugin.getConfig().getBoolean("emerald-block-buff")) {
			if (isBlockEmmy && !pd.getConfig().getBoolean("Misc.OnEmmyBlock")) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3), true);
				pd.set("Misc.OnEmmyBlock", true);
			}
			else if (!isBlockEmmy && pd.getConfig().getBoolean("Misc.OnEmmyBlock")) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 12, 3), true);
				pd.set("Misc.OnEmmyBlock", false);
			}
		}
	}
}
