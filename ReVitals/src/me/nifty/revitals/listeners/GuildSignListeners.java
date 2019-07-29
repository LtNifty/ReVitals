package me.nifty.revitals.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import me.nifty.revitals.Main;

public class GuildSignListeners extends SignListener {

	private Main plugin = Main.getPlugin(Main.class);

	@EventHandler
	public void onSignChangeEvent(SignChangeEvent e) {
		if (e.getPlayer().hasPermission("revitals.guilds.create-signs")) {
			String text = e.getLine(0);
			if (text.equalsIgnoreCase("[Brewing Guild]") || text.equalsIgnoreCase("[Farming Guild]") || text.equalsIgnoreCase("[Logging Guild]") || text.equalsIgnoreCase("[Rancher Guild]") || text.equalsIgnoreCase("[Enchanting Guild]") || text.equalsIgnoreCase("[Fishing Guild]") || text.equalsIgnoreCase("[Mining Guild]") || text.equalsIgnoreCase("[Slayer Guild]")) {
				e.setLine(0, "§1" + "[" + text.substring(1, 2).toUpperCase() + text.substring(2, text.indexOf(" ")) + text.substring(text.indexOf(" "), text.indexOf(" ") + 1).toUpperCase() + text.substring(text.indexOf(" ") + 1));
				e.setLine(1, "Right-click to see");
				e.setLine(2, "what perks this");
				e.setLine(3, "guild offers!");
			}
		}
		else
			e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e) {
		if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
		if (e.getClickedBlock().getState() instanceof Sign) {
			Sign s = (Sign) e.getClickedBlock().getState();
			String text = s.getLine(0);

			// gets owner of sign; used to check
			// if sign placer has perms
			String owner = "";
			for (MetadataValue meta : s.getMetadata("owner"))
				if (meta.getOwningPlugin().getName().equals(this.plugin.getName()))
					owner = meta.asString();

			if (Bukkit.getPlayer(owner).hasPermission("revitals.guilds.create-signs")){
				/*
				 * BREWING GUILD
				 */
				if (text.equals("§1[Brewing Guild]")) {
					Inventory inv = Bukkit.createInventory(null, 27, "§5§lBrewing Guild Perks");

					// guild perks
					ItemStack brew1 = new ItemStack(Material.CHEST);
					ItemMeta brew1Meta = brew1.getItemMeta();
					brew1Meta.setDisplayName("§5Free Brewing Materials (Daily)");
					List<String> brew1Lore = new ArrayList<String>();
					brew1Lore.add("*8 Redstone & Glowstone Dust");
					brew1Lore.add("*4 Netherwart & Gunpowder");
					brew1Lore.add("*2 of All Other Materials");
					brew1Meta.setLore(brew1Lore);
					brew1.setItemMeta(brew1Meta);
					inv.setItem(0, brew1);

					ItemStack brew2 = new ItemStack(Material.POTION);
					ItemMeta brew2Meta = brew2.getItemMeta();
					brew2Meta.setDisplayName("§5One Free Brew (Daily)");
					brew2.setItemMeta(brew2Meta);
					inv.setItem(1, brew2);

					ItemStack brew3 = new ItemStack(Material.POTION);
					PotionMeta brew3Meta = (PotionMeta) brew3.getItemMeta();
					brew3Meta.setDisplayName("§5Free Water Bottles");
					brew3Meta.clearCustomEffects();
					brew3Meta.setBasePotionData(new PotionData(PotionType.WATER));
					brew3.setItemMeta(brew3Meta);
					inv.setItem(2, brew3);

					ItemStack iron = new ItemStack(Material.IRON_BLOCK);
					ItemMeta ironMeta = iron.getItemMeta();
					ironMeta.setDisplayName("§5Discount on Brewing Materials");
					iron.setItemMeta(ironMeta);
					inv.setItem(3, iron);

					addDividers(inv);

					// level 30 perks
					ItemStack brew5 = new ItemStack(Material.CHEST);
					ItemMeta brew5Meta = brew5.getItemMeta();
					brew5Meta.setDisplayName("§5§lMore Free Brewing Materials (Daily)");
					List<String> brew5Lore = new ArrayList<String>();
					brew5Lore.add("*32 Redstone & Glowstone Dust");
					brew5Lore.add("*16 Netherwart & Gunpowder");
					brew5Lore.add("*8 of All Other Materials");
					brew5Meta.setLore(brew5Lore);
					brew5.setItemMeta(brew5Meta);
					inv.setItem(18, brew5);

					ItemStack brew6 = new ItemStack(Material.POTION);
					ItemMeta brew6Meta = brew6.getItemMeta();
					brew6Meta.setDisplayName("§5§lFour Free Brews (Daily)");
					brew6.setItemMeta(brew6Meta);
					inv.setItem(19, brew6);

					ItemStack brew7 = new ItemStack(Material.COMMAND_BLOCK);
					ItemMeta brew7Meta = brew7.getItemMeta();
					brew7Meta.setDisplayName("§5§l/brew (Pocket Brewing Stand)");
					brew7.setItemMeta(brew7Meta);
					inv.setItem(20, brew7);

					ItemStack brew8 = new ItemStack(Material.GOLD_BLOCK);
					ItemMeta brew8Meta = brew8.getItemMeta();
					brew8Meta.setDisplayName("§5§lBetter Discount on Brewing Materials");
					brew8.setItemMeta(brew8Meta);
					inv.setItem(21, brew8);

					e.getPlayer().openInventory(inv);
				}
				
				/*
				 * FARMING GUILD
				 */
				else if (text.equals("§1[Farming Guild]")) {
					Inventory inv = Bukkit.createInventory(null, 27, "§2§lFarming Guild Perks");

					ItemStack farm1 = new ItemStack(Material.CHEST);
					ItemMeta farm1Meta = farm1.getItemMeta();
					farm1Meta.setDisplayName("§2Free Food (Daily)");
					List<String> farm1Lore = new ArrayList<String>();
					farm1Lore.add("*8 Melons & Carrots");
					farm1Lore.add("*4 Bread & Baked Potatoes");
					farm1Lore.add("*1 Golden Apple & Pumpkin Pie");
					farm1Meta.setLore(farm1Lore);
					farm1.setItemMeta(farm1Meta);
					inv.setItem(0, farm1);

					ItemStack farm2 = new ItemStack(Material.BONE_MEAL);
					ItemMeta farm2Meta = farm2.getItemMeta();
					farm2Meta.setDisplayName("§2Free 32 Bone Meal (Daily)");
					farm2.setItemMeta(farm2Meta);
					inv.setItem(1, farm2);

					ItemStack farm3 = new ItemStack(Material.IRON_HOE);
					ItemMeta farm3Meta = farm3.getItemMeta();
					farm3Meta.setDisplayName("§2Free Iron Hoe (Daily)");
					farm3.setItemMeta(farm3Meta);
					inv.setItem(2, farm3);

					ItemStack iron = new ItemStack(Material.IRON_BLOCK);
					ItemMeta ironMeta = iron.getItemMeta();
					ironMeta.setDisplayName("§2Discount on Farming Materials");
					iron.setItemMeta(ironMeta);
					inv.setItem(3, iron);

					addDividers(inv);

					ItemStack farm4 = new ItemStack(Material.CHEST);
					ItemMeta farm4Meta = farm4.getItemMeta();
					farm4Meta.setDisplayName("§2§lMore Free Food (Daily)");
					List<String> farm4Lore = new ArrayList<String>();
					farm4Lore.add("*32 Melons & Carrots");
					farm4Lore.add("*16 Bread & Baked Potatoes");
					farm4Lore.add("*4 Golden Apple & Pumpkin Pie");
					farm4Lore.add("*1 Enchanted Golden Apple");
					farm4Meta.setLore(farm4Lore);
					farm4.setItemMeta(farm4Meta);
					inv.setItem(18, farm4);

					ItemStack farm5 = new ItemStack(Material.COMMAND_BLOCK);
					ItemMeta farm5Meta = farm5.getItemMeta();
					farm5Meta.setDisplayName("§2§l/bonemeal (Infinite Bone Meal)");
					farm5.setItemMeta(farm5Meta);
					inv.setItem(19, farm5);

					ItemStack farm6 = new ItemStack(Material.DIAMOND_HOE);
					ItemMeta farm6Meta = farm6.getItemMeta();
					farm6Meta.setDisplayName("§2§lFree Enchanted Diamond Hoe (Weekly)");
					farm6Meta.addEnchant(Enchantment.DURABILITY, 2, false);
					farm6.setItemMeta(farm6Meta);
					inv.setItem(20, farm6);
					
					ItemStack farm7 = new ItemStack(Material.GOLD_BLOCK);
					ItemMeta farm7Meta = farm7.getItemMeta();
					farm7Meta.setDisplayName("§2§lBetter Discount on Farming Materials");
					farm7.setItemMeta(farm7Meta);
					inv.setItem(21, farm7);

					e.getPlayer().openInventory(inv);
				}
				else if (text.equals("§1[Logging Guild]")) {

				}
				else if (text.equals("§1[Rancher Guild]")) {

				}
				else if (text.equals("§1[Enchanting Guild]")) {

				}
				else if (text.equals("§1[Fishing Guild]")) {

				}
				else if (text.equals("§1[Mining Guild]")) {

				}
				else if (text.equals("§1[Slayer Guild]")) {

				}
			}
		}
	}
}
