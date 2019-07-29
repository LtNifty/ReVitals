package me.nifty.revitals.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class SignListener implements Listener {
	@EventHandler
	public abstract void onSignChangeEvent(SignChangeEvent e);
	@EventHandler
	public abstract void onPlayerInteractEvent(PlayerInteractEvent e);
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent e) {
		if (e.getInventory().getName().equalsIgnoreCase("§5§lBrewing Guild Perks") || e.getInventory().getName().equalsIgnoreCase("§2§lFarming Guild Perks") || e.getInventory().getName().equalsIgnoreCase("§a§lLogging Guild Perks") || e.getInventory().getName().equalsIgnoreCase("§4§lRancher Guild Perks") || e.getInventory().getName().equalsIgnoreCase("§d§lEnchanting Guild Perks") || e.getInventory().getName().equalsIgnoreCase("§b§lFishing Guild Perks") || e.getInventory().getName().equalsIgnoreCase("§7§lMining Guild Perks") || e.getInventory().getName().equalsIgnoreCase("§8§lSlayer Guild Perks"))
			e.setCancelled(true);
	}
	
	public void addDividers(Inventory inv) {
		ItemStack divider = new ItemStack(Material.STICK);
		ItemMeta dividerMeta = divider.getItemMeta();
		dividerMeta.setDisplayName("§e§l▲ Guild Perks // ▼ Level 30 Perks");
		divider.setItemMeta(dividerMeta);
		for (int i = 9; i < 18; i++) {
			inv.setItem(i, divider);
		}
	}
	
	public void addGlow(ItemStack item) {
	}
}
