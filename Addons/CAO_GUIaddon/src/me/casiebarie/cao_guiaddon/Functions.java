package me.casiebarie.cao_guiaddon;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.casiebarie.casieattractionoperate.API;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;

public class Functions {
	private Main plugin;
	private API caoAPI;
	private static FileConfiguration caoConfig;
	private static FileConfiguration config;
	private static ArrayList<String> hasINVopen = new ArrayList<>();
	
	public Functions(Main plugin, API caoAPI) {
		this.plugin = plugin;
		this.caoAPI = caoAPI;
		reloadConfig(null);
	}
	
	public FileConfiguration getCAOconfig() {return caoConfig;}
	public FileConfiguration getConfig() {return config;}
	
	public void reloadConfig(CommandSender sender) {
		File caoConfigFile = new File(Bukkit.getPluginManager().getPlugin("CasieAttractionOperate").getDataFolder() + "/config.yml");
		File configFile = new File(plugin.getDataFolder() + "/config.yml");
		caoConfig = YamlConfiguration.loadConfiguration(caoConfigFile);
		config = YamlConfiguration.loadConfiguration(configFile);
		plugin.saveDefaultConfig();
		if(sender != null) {caoAPI.sendMessage(sender, "Reload", "", "GUIaddon Config");}
	}
	
	public void runCAOmethod(CommandSender sender, String attractionName, String mode) {
		if(mode.equals("RESTRAINTS")) {caoAPI.restraints(sender, attractionName);
		} else if (mode.equals("GATES")) {caoAPI.gates(sender, attractionName);
		} else if (mode.equals("RELEASE")) {caoAPI.release(sender, attractionName);
		} else if (mode.equals("POWER")) {caoAPI.power(sender, attractionName, "TOGGLE");}
	}
	
	public void sendMessage(CommandSender sender, String msgName, String args) {
		if(sender == null) {return;}
		if(config.contains(".Messages." + msgName) && !config.getString(".Messages." + msgName).equals("")) {
			String prefix = caoConfig.getString(".Messages.Prefix").equals("") ? "" : caoConfig.getString(".Messages.Prefix");
			String msg = config.getString(".Messages." + msgName).replaceAll("%args%", String.valueOf(args) + "&r").replaceAll("%prefix%", prefix);
			if(plugin.papiPresent()) {msg = PlaceholderAPI.setPlaceholders((OfflinePlayer) sender, msg);}
			sender.sendMessage(Color(msg));
		}
	}
	
	public String setPlaceholders(CommandSender sender, String msg) {
		if(plugin.papiPresent()) {return PlaceholderAPI.setPlaceholders((OfflinePlayer) sender, msg);}
		return msg;
	}
	
	public String Color(String msg) {return ChatColor.translateAlternateColorCodes('&', msg);}
	
	public void setINVopen(UUID playerUuid) {hasINVopen.add(playerUuid.toString());}
	public void setINVclosed(UUID playerUuid) {hasINVopen.remove(playerUuid.toString());}
	public ArrayList<String> getINVopen() {return hasINVopen;}
}
