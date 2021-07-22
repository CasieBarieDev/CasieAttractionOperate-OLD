package me.casiebarie.cao_guiaddon.other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.casiebarie.cao_guiaddon.Functions;
import me.casiebarie.cao_guiaddon.Main;

public class TabComplete implements TabCompleter {
	private Functions f;
	private FileConfiguration caoConfig;
	private enum mode {Open, ReloadConfig, Info}
	
	public TabComplete(Main plugin, Functions f) {
		this.f = f;
		plugin.getCommand("CAOMENU").setTabCompleter(this);
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		caoConfig = f.getCAOconfig();
		List<String> completions = new ArrayList<String>();
		if(cmd.getName().equalsIgnoreCase("CAOMENU")) {
			if(args.length == 1) {
				if(!args[0].equals("")) {
					for(mode value : mode.values()) {
						if (value.name().toUpperCase().startsWith(args[0].toUpperCase())) {completions.add(value.name());}
					}
				} else {for(mode value : mode.values()) {if (value.name().toUpperCase().startsWith(args[0].toUpperCase())) {completions.add(value.name());}}}
			} else if (args.length == 2 && args[0].equalsIgnoreCase("Open")) {
				if(!args[1].equals("")) {
					for(String attractionName : caoConfig.getConfigurationSection(".Attractions").getKeys(false)) {
						if(attractionName.toUpperCase().startsWith(args[1].toUpperCase())) {completions.add(attractionName);}
					}
				} else {for(String attractionName : caoConfig.getConfigurationSection(".Attractions").getKeys(false)) {completions.add(attractionName);}}
			} else if (args.length == 3 && args[0].equalsIgnoreCase("Open")) {
				if(args[2].equals("")) {
					for(Player player : Bukkit.getOnlinePlayers()) {
						if(player.getName().toUpperCase().startsWith(args[2].toUpperCase())) {completions.add(player.getName());}
					}
				} else {for(Player player : Bukkit.getOnlinePlayers()) {completions.add(player.getName());}}
			}
		}
		Collections.sort(completions);
		return completions;
	}
}