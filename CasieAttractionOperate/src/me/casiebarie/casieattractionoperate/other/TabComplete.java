package me.casiebarie.casieattractionoperate.other;

import org.bukkit.configuration.file.FileConfiguration;

import me.casiebarie.casieattractionoperate.Functions;
import me.casiebarie.casieattractionoperate.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabComplete implements TabCompleter {
	@SuppressWarnings("unused")
	private Main plugin;
	private Functions f;
	private FileConfiguration config;
	public TabComplete(Main plugin, Functions f) {
		this.plugin = plugin;
		this.f = f;
		plugin.getCommand("CAO").setTabCompleter(this);
		plugin.getCommand("CAOADMIN").setTabCompleter(this);
		plugin.getCommand("CAOINFO").setTabCompleter(this);
	}
	
	private enum trueFalse{TRUE, FALSE}
	private enum powerMode{TOGGLE, DONE}
	private enum reloadMode{ReloadConfig, ReloadAttraction}
	private enum infoMode{Setup, Commands, Placeholders, Support}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		config = f.GetConfig();
		ArrayList<String> modes = f.GetCmds();
		List<String> completions = new ArrayList<String>();
		//CAO
		if(cmd.getName().equalsIgnoreCase("CAO")) {
			if(args.length == 1) {
				if (!args[0].equals("")) {
					for(String attraction : config.getConfigurationSection(".Attractions").getKeys(false)) {
						if (attraction.toUpperCase().startsWith(args[0].toUpperCase())) {completions.add(attraction);}
					}
				} else {for(String attraction : config.getConfigurationSection(".Attractions").getKeys(false)) {completions.add(attraction);}}
			}
			if(args.length == 2) {
				if(!args[1].equals("")) {
					for(String mode : modes) {
						if (mode.toUpperCase().startsWith(args[1].toUpperCase())) {completions.add(mode);}
					}
				} else {for(String mode : modes) {completions.add(mode);}}
			}
			if(args.length == 3 && (args[1].toUpperCase().equals(modes.get(5).toUpperCase()) || args[1].toUpperCase().equals(modes.get(4).toUpperCase()))) {
				if(!args[2].equals("")) {
					for(trueFalse value : trueFalse.values()) {
						if (value.toString().toUpperCase().startsWith(args[2].toUpperCase())) {completions.add(value.name().toLowerCase());}
					}
				} else {for(trueFalse value : trueFalse.values()) {completions.add(value.toString().toLowerCase());}}
			} else if (args.length == 3 && args[1].toUpperCase().equals(modes.get(3).toUpperCase())) {
				if(!args[2].equals("")) {
					for(powerMode value : powerMode.values()) {
						if(value.name().toUpperCase().startsWith(args[2].toUpperCase())) {completions.add(value.name());}
					}
				} else {for(powerMode value : powerMode.values()) {completions.add(value.name());}}
			}
		}
		//CAOADMIN
		if(cmd.getName().equalsIgnoreCase("CAOADMIN")) {
			if(args.length == 1) {
				if(!args[0].equals("")) {
					for(reloadMode value : reloadMode.values()) {
						if(value.name().toUpperCase().startsWith(args[0].toUpperCase())) {completions.add(value.name());}}
				} else {for(reloadMode value : reloadMode.values()) {completions.add(value.name());}}
			} else if (args.length == 2 && args[0].toUpperCase().equals("RELOADATTRACTION")) {
				if(!args[1].equals("")) {
					for(final String attraction : this.config.getConfigurationSection(".Attractions").getKeys(false)) {
						if(attraction.toUpperCase().startsWith(args[1].toUpperCase())) {completions.add(attraction);}}
				} else {for(final String attraction : this.config.getConfigurationSection(".Attractions").getKeys(false)) {completions.add(attraction);}}
			}
		}
		//CAOINFO
		if(cmd.getName().equalsIgnoreCase("CAOINFO")) {
			if(args.length == 1) {
				if(!args[0].equals("")) {
					for(infoMode value : infoMode.values()) {if(value.name().toUpperCase().startsWith(args[0].toUpperCase())) {completions.add(value.name());}}
				} else {for(infoMode value : infoMode.values()) {completions.add(value.name());}}
			}
		}
		Collections.sort(completions);
		return completions;
	}
}