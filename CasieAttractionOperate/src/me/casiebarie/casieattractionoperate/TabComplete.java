package me.casiebarie.casieattractionoperate;

import org.bukkit.configuration.file.FileConfiguration;

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
		plugin.getCommand("CAO").setTabCompleter((TabCompleter)this);
		plugin.getCommand("CAOADMIN").setTabCompleter((TabCompleter)this);
	}
	
	private enum TrueFalse{TRUE, FALSE}
	private enum powermode{TOGGLE, DONE}
	private enum reloadmode{ReloadConfig, ReloadAttraction}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		config = f.GetConfig();
		ArrayList<String> modes = f.GetCmds();
		List<String> completions = new ArrayList<String>();
		if(cmd.getName().equalsIgnoreCase("CAO")) {
			if(args.length == 1) {
				if (!args[0].equals("")) {
					for(String attraction : config.getConfigurationSection(".Attractions").getKeys(false)) {
						if (attraction.toUpperCase().startsWith(args[0].toUpperCase())) {
							completions.add(attraction);
						}
					}
				} else {
					for(String attraction : config.getConfigurationSection(".Attractions").getKeys(false)) {
						completions.add(attraction);
					}
				}
			}
			if(args.length == 2) {
				if(!args[1].equals("")) {
					for(String mode : modes) {
						if (mode.toUpperCase().startsWith(args[1].toUpperCase())) {
							completions.add(mode);
						}
					}
				} else {
					for(String mode : modes) {
						completions.add(mode);
					}
				}
			}
			if(args.length == 3 && (args[1].toUpperCase().equals(modes.get(5).toUpperCase()) || args[1].toUpperCase().equals(modes.get(4).toUpperCase()))) {
				if(!args[2].equals("")) {
					TrueFalse[] values;
					for(int length = (values = TrueFalse.values()).length, i = 0; i < length; ++i) {
						TrueFalse TrueFalseMode = values[i];
						if (TrueFalseMode.name().toUpperCase().startsWith(args[2].toUpperCase())) {
							completions.add(TrueFalseMode.name().toLowerCase());
						}
					}
				} else {
					TrueFalse[] values;
					for(int length = (values = TrueFalse.values()).length, i = 0; i < length; ++i) {
						TrueFalse TrueFalseMode = values[i];
						completions.add(TrueFalseMode.name().toLowerCase());
					}
				}
			} else if (args.length == 3 && args[1].toUpperCase().equals(modes.get(3).toUpperCase())) {
				if(!args[2].equals("")) {
					powermode[] values;
					for(int length = (values = powermode.values()).length, i = 0; i < length; ++i) {
						powermode powermodes = values[i];
						if(powermodes.name().toUpperCase().startsWith(args[2].toUpperCase())) {
							completions.add(powermodes.name());
						}
					}
				} else {
					powermode[] values;
					for(int length = (values = powermode.values()).length, i = 0; i < length; ++i) {
						powermode powermodes = values[i];
						completions.add(powermodes.name());
					}
				}
			}
		}
		if (cmd.getName().equalsIgnoreCase("CAOADMIN")) {
			if(args.length == 1) {
				if(!args[0].equals("")) {
					reloadmode[] values;
					for(int length = (values = reloadmode.values()).length, i = 0; i < length; ++i) {
						reloadmode reloadmodes = values[i];
						if(reloadmodes.name().toUpperCase().startsWith(args[0].toUpperCase())) {
							completions.add(reloadmodes.name());
						}
					}
				} else {
					reloadmode[] values;
					for(int length = (values = reloadmode.values()).length, i = 0; i < length; ++i) {
						reloadmode reloadmodes = values[i];
						completions.add(reloadmodes.name());
					}
				}
			} else if (args.length == 2 && args[0].toUpperCase().equals("RELOADATTRACTION")) {
				if(!args[1].equals("")) {
					for(final String attraction : this.config.getConfigurationSection(".Attractions").getKeys(false)) {
						if(attraction.toUpperCase().startsWith(args[1].toUpperCase())) {
							completions.add(attraction);
						}
					}
				} else {
					for(final String attraction : this.config.getConfigurationSection(".Attractions").getKeys(false)) {
						completions.add(attraction);
					}
				}
			}
		}
		Collections.sort(completions);
		return completions;
	}
}
