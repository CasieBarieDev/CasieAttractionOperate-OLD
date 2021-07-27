package me.casiebarie.casieattractionoperate;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.md_5.bungee.api.ChatColor;

public class API {
	private Main plugin;
	private CAO cao;
	private Functions f;
	private FileConfiguration config;
	public API(Main plugin, CAO cao, Functions f) {
		this.plugin = plugin;
		this.cao = cao;
		this.f = f;
	}
	
	//CAO
	public void restraints(CommandSender sender, String attractionName) {
		File aFile1 = getaFile1(attractionName);
		FileConfiguration aFile = YamlConfiguration.loadConfiguration(aFile1);
		String aWorld = aFile.getString(".Settings.World");
		if(aFile1.exists()) {cao.restraints(sender, attractionName, aFile1, aFile, aWorld);}
	}
	public void gates(CommandSender sender, String attractionName) {
		File aFile1 = getaFile1(attractionName);
		FileConfiguration aFile = YamlConfiguration.loadConfiguration(aFile1);
		String aWorld = aFile.getString(".Settings.World");
		if(aFile1.exists()) {cao.gates(sender, attractionName, aFile1, aFile, aWorld);}
	}
	public void release(CommandSender sender, String attractionName) {
		File aFile1 = getaFile1(attractionName);
		FileConfiguration aFile = YamlConfiguration.loadConfiguration(aFile1);
		String aWorld = aFile.getString(".Settings.World");
		if(aFile1.exists()) {cao.release(sender, attractionName, aFile1, aFile, aWorld);}
	}
	public void power(CommandSender sender, String attractionName, String mode) {
		File aFile1 = getaFile1(attractionName);
		FileConfiguration aFile = YamlConfiguration.loadConfiguration(aFile1);
		String aWorld = aFile.getString(".Settings.World");
		if(aFile1.exists()) {cao.power(sender, attractionName, mode, aFile1, aFile, aWorld);}
	}
	public void busy(CommandSender sender, String attractionName, String bool) {
		File aFile1 = getaFile1(attractionName);
		FileConfiguration aFile = YamlConfiguration.loadConfiguration(aFile1);
		String aWorld = aFile.getString(".Settings.World");
		if(aFile1.exists()) {cao.busy(sender, attractionName, bool, aFile1, aFile, aWorld);}
	}
	public void station(CommandSender sender, String attractionName, String bool) {
		File aFile1 = getaFile1(attractionName);
		FileConfiguration aFile = YamlConfiguration.loadConfiguration(aFile1);
		String aWorld = aFile.getString(".Settings.World");
		if(aFile1.exists()) {cao.station(sender, attractionName, bool, aFile1, aFile, aWorld);}
	}
	
	//Functions
	public boolean isLegacy() {return f.isLegacy();}
	public ArrayList<String> GetCmds() {return f.GetCmds();}
	public void sendMessage(CommandSender sender, String msgName, String attractionName, String args) {f.sendMessage(sender, msgName, attractionName, args);}
	public String getVariable(String attractionName, String mode) {
		File aFile1 = getaFile1(attractionName);
		FileConfiguration aFile = YamlConfiguration.loadConfiguration(aFile1);
		String aWorld = aFile.getString(".Settings.World");
		if(attractionName.equals("Sample")) {return "&c! YOU CANNOT USE SAMPLE !";}
		if(aFile1.exists()) {return ChatColor.translateAlternateColorCodes('&', f.getVar(aFile1, aFile, aWorld, mode));}
		return "&c! ATTRACTION " + attractionName + " NOT FOUND !";
	}
	//Getfile
	private File getaFile1(String attractionName) {
		config = f.GetConfig();
		File aFile1 = null;
		aFile1 = new File(plugin.getDataFolder() + "/" + config.getString(".Attractions." + attractionName));
		return aFile1;
	}
}
