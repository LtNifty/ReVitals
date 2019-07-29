package me.nifty.revitals.shelfed;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//import java.util.logging.Level;
//
//import org.bukkit.Bukkit;
//import org.bukkit.ChatColor;
//
//import me.nifty.revitals.Main;
//
public class Locale {
//
//	private String propFileName = "messages_";
//	private Main plugin = Main.getPlugin(Main.class);
//	private Properties prop = new Properties();
//	InputStream stream;
//
//	public Locale() {
//		switch (plugin.getConfig().getString("locale")) {
//		default:
//			Bukkit.getLogger().log(Level.SEVERE, this.get("invalidLocale"));
//			propFileName += "en.properties";
//			break;
//		case "en":
//			propFileName += "en.properties";
//			break;
//		}
//
//		stream = getClass().getClassLoader().getResourceAsStream(propFileName);
//		try {
//			prop.load(stream);
//		} catch (IOException e) {
//			
//		}
//	}
//
//	public String get(String path) {
//		String str = "";
//		try {
//			stream.reset();
//			prop.load(stream);
//			str = ChatColor.translateAlternateColorCodes('&', prop.getProperty(path));
//		} catch (IOException e) {
//			Bukkit.getLogger().log(Level.SEVERE, "Something has gone horribly wrong!");
//		}
//		return str;
//	}
//
//	public String getAndAdd(String path, Object additive) {
//		String str = this.get(path);
//		return str.replaceAll("{0}", additive.toString());
//	}
}