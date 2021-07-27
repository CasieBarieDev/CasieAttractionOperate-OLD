package me.casiebarie.casieattractionoperate;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;

public class Functions {
	private static FileConfiguration config;
	private Main plugin;
	public Functions(Main plugin) {
		this.plugin = plugin;
		reloadConfig(null);
		isLegacy();
	}

	public boolean isLegacy() {
		final String BukkitVersion = Bukkit.getServer().getClass().getPackage().getName().replace("org.bukkit.craftbukkit.v", "");
		final List<String> LegacyVersions = Arrays.asList("1_8_R1", "1_8_R2", "1_8_R3", "1_9_R1", "1_9_R2", "1_10_R1", "1_11_R1", "1_12_R1");
		final List<String> Versions = Arrays.asList("1_13_R1", "1_13_R2", "1_14_R1", "1_15_R1", "1_16_R1", "1_16_R2", "1_16_R3", "1_17_R1", "1_17_R2");
		if(LegacyVersions.contains(BukkitVersion)) {return true;}
		else if (Versions.contains(BukkitVersion)) {return false;}
		else {
			Bukkit.getLogger().warning("Incompatible version. Disabling plugin  (" + BukkitVersion + ")");
			Bukkit.getServer().getPluginManager().disablePlugin((Plugin)this.plugin);
			return false;
		}
	}

	public void reloadConfig(final CommandSender sender) {
		File cFile = new File(this.plugin.getDataFolder() + "/config.yml");
		config = (FileConfiguration)YamlConfiguration.loadConfiguration(cFile);
		plugin.saveDefaultConfig();
		if(sender != null) {sendMessage(sender, "Reload", "", "Config");}
	}
	
	public String getUsageMSG() {return "<" + GetCmds().get(0) + "/" + GetCmds().get(1) + "/" + GetCmds().get(2) + "/" + GetCmds().get(3) + "/" + GetCmds().get(4) + "/" + GetCmds().get(5) + ">";}

	public FileConfiguration GetConfig() {return Functions.config;}
	public ArrayList<String> GetCmds() {
		ArrayList<String> modes = new ArrayList<String>();
		modes.add(0, config.getString(".Commands.RESTRAINTS"));
		modes.add(1, config.getString(".Commands.GATES"));
		modes.add(2, config.getString(".Commands.RELEASE"));
		modes.add(3, config.getString(".Commands.POWER"));
		modes.add(4, config.getString(".Commands.BUSY"));
		modes.add(5, config.getString(".Commands.STATION"));
		return modes;
	}

	public String getVar(File aFile1, FileConfiguration aFile, String aWorld, String mode) {
		if(mode.toUpperCase().equals(GetCmds().get(0).toUpperCase()) || mode.toUpperCase().equals("RESTRAINTS")) {
			//Restraints
			if(aFile.getBoolean(".Status.RESTRAINTS") == false && aFile.getBoolean(".Status.STATION") == true && aFile.getString(".Status.POWER").equals("Enabled")) {
				return config.getString(".Variables.Closed");
			} else if(aFile.getBoolean(".Status.RESTRAINTS") == true && aFile.getBoolean(".Status.STATION") == true && aFile.getString(".Status.POWER").equals("Enabled")) {
				return config.getString(".Variables.Open");
			} else {
				return config.getString(".Variables.NoClosed");
			}
		} else if (mode.toUpperCase().equals(GetCmds().get(1).toUpperCase()) || mode.toUpperCase().equals("GATES")) {
			//GATES
			if(aFile.getBoolean(".Status.GATES") == false && aFile.getBoolean(".Status.STATION") == true && aFile.getString(".Status.POWER").equals("Enabled")) {
				return config.getString(".Variables.Closed");
			} else if(aFile.getBoolean(".Status.GATES") == true && aFile.getBoolean(".Status.STATION") == true && aFile.getString(".Status.POWER").equals("Enabled")) {
				return config.getString(".Variables.Open");
			} else {
				return config.getString(".Variables.NoClosed");
			}
		} else if (mode.toUpperCase().equals(GetCmds().get(2).toUpperCase()) || mode.toUpperCase().equals("RELEASE")) {
			//RELEASE
			if(aFile.getBoolean(".Status.STATION") == true && aFile.getString(".Status.POWER").equals("Enabled") && aFile.getBoolean(".Status.GATES") == false && aFile.getBoolean(".Status.RESTRAINTS") == false && aFile.getBoolean(".Status.BUSY") == false) {
				return config.getString(".Variables.ReleaseAllowed");
			} else {
				return config.getString(".Variables.ReleaseDisallowed");
			}
		} else if (mode.toUpperCase().equals(GetCmds().get(3).toUpperCase()) || mode.toUpperCase().equals("POWER")) {
			//POWER
			if(aFile.getString(".Status.POWER").equals("Enabled") && aFile.getBoolean(".Status.RELEASE") == true) {
				return config.getString(".Variables.PowerOn");
			} else if(aFile.getString(".Status.POWER").equals("Enabled") && aFile.getBoolean(".Status.RELEASE") == false) {
				return config.getString(".Variables.NoPowerOn");
			} else if(aFile.getString(".Status.POWER").equals("Disabled")) {
				return config.getString(".Variables.PowerOff");
			} else if(aFile.getString(".Status.POWER").equals("Startup")) {
				return config.getString(".Variables.PowerStartup");
			} else if(aFile.getString(".Status.POWER").equals("Shutdown")) {
				return config.getString(".Variables.PowerShutdown");
			}
		} else if (mode.toUpperCase().equals(GetCmds().get(4).toUpperCase()) || mode.toUpperCase().equals("BUSY")) {
			//BUSY
			return (aFile.getBoolean(".Status.BUSY")) ? "true" : "false";
		} else if (mode.toUpperCase().equals(GetCmds().get(5).toUpperCase()) || mode.toUpperCase().equals("STATION")) {
			//STATION
			return (aFile.getBoolean(".Status.STATION")) ? "true" : "false";
		} else {return "&c! MODE NOT FOUND !";}
		return null;
	}

	public void sendMessage(CommandSender sender, String msgName, String attractionName, String args) {
		if(sender == null) {return;}
		if(config.contains(".Messages." + msgName) && !config.getString(".Messages." + msgName).equals("")) {
			String prefix = config.getString(".Messages.Prefix").equals("") ? "" : config.getString(".Messages.Prefix");
			String msg = config.getString(".Messages." + msgName).replaceAll("%attraction%", attractionName).replaceAll("%args%", String.valueOf(args) + "&r").replaceAll("%prefix%", prefix);
			if(plugin.papiPresent() && sender instanceof Player) {msg = PlaceholderAPI.setPlaceholders((Player) null, msg);}
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
		}
	}
}
