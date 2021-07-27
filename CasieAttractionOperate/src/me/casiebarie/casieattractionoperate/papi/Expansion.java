package me.casiebarie.casieattractionoperate.papi;

import org.bukkit.entity.Player;

import me.casiebarie.casieattractionoperate.Main;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class Expansion extends PlaceholderExpansion {
	private Main plugin;
	public Expansion(Main plugin) {this.plugin = plugin;}
	@Override
	public boolean persist() {return true;}
	@Override
	public boolean canRegister() {return true;}
	@Override
	public String getAuthor() {return plugin.getDescription().getAuthors().toString();}
	@Override
	public String getIdentifier() {return "cao";}
	@Override
	public String getVersion() {return plugin.getDescription().getVersion();}
	@Override
	public String onPlaceholderRequest(Player player, String identifier) {
		if(player == null) {return "";}
		String[] identifierSplit = identifier.split("_");
		if(identifierSplit.length == 2) {return plugin.api.getVariable(identifierSplit[0], identifierSplit[1]);}
		return null;
	}
}
