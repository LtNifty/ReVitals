package me.nifty.revitals;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.nifty.revitals.commands.ClearboardCommand;
import me.nifty.revitals.commands.CleartagCommand;
import me.nifty.revitals.commands.CopytagCommand;
import me.nifty.revitals.commands.MetaboardCommand;
import me.nifty.revitals.commands.NicknameCommand;
import me.nifty.revitals.commands.PastetagCommand;
import me.nifty.revitals.commands.ReloreCommand;
import me.nifty.revitals.commands.RenameCommand;
import me.nifty.revitals.listeners.ChatListener;
import me.nifty.revitals.listeners.EmeraldBlockListener;
import me.nifty.revitals.listeners.FallDamageListener;
import me.nifty.revitals.listeners.GuildSignListeners;
import me.nifty.revitals.listeners.ImmegrationListener;
import me.nifty.revitals.listeners.PvPListener;
import me.nifty.revitals.listeners.SpongeListener;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class Main extends JavaPlugin {

	public static Economy economy = null;
	public static Permission permission = null;

	@Override
	public void onEnable() {
		new RenameCommand(this);
		new ReloreCommand(this);
		new MetaboardCommand(this);
		new CopytagCommand(this);
		new PastetagCommand(this);
		new CleartagCommand(this);
		new NicknameCommand(this);
		new ClearboardCommand(this);
		Bukkit.getServer().getPluginManager().registerEvents(new GuildSignListeners(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new SpongeListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EmeraldBlockListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new FallDamageListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new ChatListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new ImmegrationListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PvPListener(), this);
		new Sidebar();
		new WorldTime();
		this.createDirs();
		this.playtime();
		setupEconomy();
		setupPermissions();
	}

	@Override
	public void onDisable() {
	}

	public void createDirs() {
		if (!this.getDataFolder().exists())
			this.getDataFolder().mkdir();
		File userData = new File(this.getDataFolder(), "userdata");
		if (!userData.exists()) {
			userData.mkdir();
		}
	}

	// playtime is updated every real-time minute.
	// a playime of 72 equates to 72 minutes on the server.
	public void playtime() {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				if (Main.hasPlayers())
					for (Player player : Bukkit.getOnlinePlayers()) {
						PlayerDataHandler pd = new PlayerDataHandler(player);
						pd.incNewPlaytime();
						if (getConfig().getInt("citizen-time-req") != 0 && pd.getConfig().getInt("Updateables.New Playtime") == getConfig().getInt("citizen-time-req")) {
							player.sendMessage(ChatColor.GOLD + "Your playtime has reached 2 hours. You are now a Citizen!");
							Main.permission.playerAddGroup(null, player, "Citizen");
							Main.permission.playerRemoveGroup(null, player, "Wanderer");
						}

					}
			}
		},0, 20 * 60);
	}

	//this can be used to stop things when there is no players on
	//good for keeping console clean
	public static boolean hasPlayers() {
		return Bukkit.getServer().getOnlinePlayers().size() == 0;
	}

	public static String getGroup(Player p, boolean useColor) {
		String s = "";

		if (p.hasPermission("revitals.owner")) {
			if (useColor)
				return "§c§lO§6§lw§e§ln§a§le§b§lr§r";
			return "Owner";
		}
		else if (p.hasPermission("revitals.admin")) {
			if (useColor)
				s += ChatColor.RED + "" + ChatColor.BOLD;
			return s += "Admin";
		}
		else if (p.hasPermission("revitals.mod")) {
			if (useColor)
				s += ChatColor.DARK_PURPLE + "" + ChatColor.BOLD;
			return s += "Mod";
		}
		else if (p.hasPermission("revitals.trusted")) {
			if (useColor)
				s += ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD;
			return s += "Trusted";
		}
		else if (p.hasPermission("revitals.wanderer")) {
			if (useColor)
				s += ChatColor.GRAY;
			return s += "Wanderer";
		}
		else if (p.hasPermission("revitals.citizen")) {
			if (useColor)
				s += ChatColor.WHITE;
			return s += "Citizen";
		}
		else if (p.hasPermission("revitals.noble")) {
			if (useColor)
				s += ChatColor.YELLOW;
			return s += "Noble";
		}
		else if (p.hasPermission("revitals.merchant")) {
			if (useColor)
				s += ChatColor.GOLD;
			return s += "Merchant";
		}
		else if (p.hasPermission("revitals.knight")) {
			if (useColor)
				s += ChatColor.DARK_GREEN;
			return s += "Knight";
		}
		else if (p.hasPermission("revitals.baron")) {
			if (useColor)
				s += ChatColor.GREEN;
			return s += "Baron";
		}
		else if (p.hasPermission("revitals.duke")) {
			if (useColor)
				s += ChatColor.DARK_PURPLE;
			return s += "Duke";
		}
		else if (p.hasPermission("revitals.chancellor")) {
			if (useColor)
				s += ChatColor.DARK_AQUA;
			return s += "Chancellor";
		}
		else if (p.hasPermission("revitals.viceroy")) {
			if (useColor)
				s += ChatColor.AQUA;
			return s += "Viceroy";
		}
		else if (p.hasPermission("revitals.guardian")) {
			if (useColor)
				s += ChatColor.BLUE;
			return s += "Guardian";
		}
		else if (p.hasPermission("revitals.avatar")) {
			if (useColor)
				s += ChatColor.DARK_RED;
			return s += "Avatar";
		}
		else
			return "ERROR";
	}

	public static String getSuperGroupTag(Player p, boolean useColor) {
		String s = "";
		if (p.hasPermission("revitals.sapphire")) {
			if (useColor)
				s += ChatColor.BLUE;
			return s += unicize(":06e9:") + " ";
		}
		else if (p.hasPermission("revitals.ruby")) {
			if (useColor)
				s += ChatColor.RED;
			return s += unicize(":0f06:") + " ";
		}
		else if (p.hasPermission("revitals.dragonstone")) {
			if (useColor)
				s += ChatColor.LIGHT_PURPLE;
			return s += unicize(":0f3a:") + " ";
		}
		else if (p.hasPermission("revitals.veteran")) {
			if (useColor)
				s += ChatColor.GREEN;
			return s += unicize(":272f:") + " ";
		}
		else
			return "";
	}

	private final static HashMap<String,Integer> unicodes = new HashMap<String,Integer>(); {
		unicodes.put(":airplane:", 0x2708); unicodes.put(":asterism:", 0x2042); unicodes.put(":notes:", 0x266b);
		unicodes.put(":biohazard:", 0x2623); unicodes.put(":cloud:", 0x2601); unicodes.put(":coffee:", 0x2615);
		unicodes.put(":comet:", 0x2604); unicodes.put(":flower:", 0x2698); unicodes.put(":frowny:", 0x2639);
		unicodes.put(":gear:", 0x2699); unicodes.put(":russia:", 0x262d); unicodes.put(":heart:", 0x2764);
		unicodes.put(":peace:", 0x262e); unicodes.put(":face:", 0x3020); unicodes.put(":note:", 0x266a);
		unicodes.put(":radioactive:", 0x2622); unicodes.put(":skull:", 0x2620); unicodes.put(":smiley:", 0x263a);
		unicodes.put(":snowflake:", 0x2744); unicodes.put(":snowman:", 0x2603); unicodes.put(":squiggly:", 0x2368);
		unicodes.put(":star:", 0x2605); unicodes.put(":sun:", 0x2600); unicodes.put(":umbrella:", 0x2602);
		unicodes.put(":lightning:", 0x26a1); unicodes.put(":yinyang:", 0x262f); unicodes.put(":spades:", 0x2660);
		unicodes.put(":clubs:", 0x2663); unicodes.put(":hearts:", 0x2665); unicodes.put(":diamonds:", 0x2666);
		unicodes.put(":smiley2:", 0x263b); unicodes.put(":wking:", 0x2654); unicodes.put(":wqueen:", 0x2655);
		unicodes.put(":wrook:", 0x2656); unicodes.put(":wbishop:", 0x2657); unicodes.put(":wknight:", 0x2658);
		unicodes.put(":wpawn:", 0x2659); unicodes.put(":bking:", 0x2660); unicodes.put(":bqueen:", 0x2661);
		unicodes.put(":brook:", 0x2662); unicodes.put(":bbishop:", 0x2663); unicodes.put(":bknight:", 0x2664);
		unicodes.put(":bpawn:", 0x2665);
	}

	public static String unicize(String string) {
		String s = string;
		StringBuffer buf = new StringBuffer();
		Pattern pat = Pattern.compile(":[0-9a-f]{4}:");
		Matcher mat = pat.matcher(s);

		while (mat.find()) 
			mat.appendReplacement(buf, Character.toString((char) Integer.parseInt(mat.group().substring(1, 5), 16)));
		mat.appendTail(buf); s = buf.toString();

		for (String key : unicodes.keySet()) 
			s = s.replaceAll(key, Character.toString((char) unicodes.get(key).intValue()));
		return s;
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}

	private boolean setupPermissions()
	{
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
		return (permission != null);
	}
}
