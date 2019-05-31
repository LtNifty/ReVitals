package me.nifty.revitals;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.Essentials;

import me.nifty.revitals.commands.AddloreCommand;
import me.nifty.revitals.commands.ClearloreCommand;
import me.nifty.revitals.commands.ClearnameCommand;
import me.nifty.revitals.commands.CleartagsCommand;
import me.nifty.revitals.commands.ReloreCommand;
import me.nifty.revitals.commands.RenameCommand;
import me.nifty.revitals.listeners.EmeraldBlockListener;
import me.nifty.revitals.listeners.EmeraldListener;
import me.nifty.revitals.listeners.FallDamageListener;
import me.nifty.revitals.listeners.PvPListener;
import me.nifty.revitals.listeners.SpongeListener;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class Main extends JavaPlugin {
	
	public static ArrayList<UUID> safeFall = new ArrayList<UUID>();
	public static ArrayList<UUID> emmyUsers = new ArrayList<UUID>();
	public static Permission permission = null;
    public static Economy economy = null;
    public static Chat chat = null;
	
	@Override
	public void onEnable() {
		Essentials ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
		new RenameCommand(this);
		new ReloreCommand(this);
		new AddloreCommand(this);
		new ClearnameCommand(this);
		new ClearloreCommand(this);
		new CleartagsCommand(this);
		Bukkit.getServer().getPluginManager().registerEvents(new GuildSignListeners(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new SpongeListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EmeraldBlockListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new FallDamageListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EmeraldListener(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PvPListener(this, ess), this);
		setupEconomy();
		setupPermissions();
		setupChat();
	}
	
	 private boolean setupPermissions()
	    {
	        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
	        if (permissionProvider != null) {
	            permission = permissionProvider.getProvider();
	        }
	        return (permission != null);
	    }

	    private boolean setupChat()
	    {
	        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
	        if (chatProvider != null) {
	            chat = chatProvider.getProvider();
	        }

	        return (chat != null);
	    }

	    private boolean setupEconomy()
	    {
	        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
	        if (economyProvider != null) {
	            economy = economyProvider.getProvider();
	        }

	        return (economy != null);
	    }
}
