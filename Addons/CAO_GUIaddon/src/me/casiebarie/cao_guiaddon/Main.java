package me.casiebarie.cao_guiaddon;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.casiebarie.cao_guiaddon.other.Commands;
import me.casiebarie.cao_guiaddon.other.Info;
import me.casiebarie.cao_guiaddon.other.TabComplete;
import me.casiebarie.casieattractionoperate.API;

public class Main extends JavaPlugin {
	public me.casiebarie.casieattractionoperate.Main cao;
	public API caoAPI;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		caoAPI = getCAOapi();
		if(caoAPI != null) {
			Functions f = new Functions(this, caoAPI);
			Menu menu = new Menu(this, f, caoAPI);
			new EventListener(this, f, caoAPI, menu);
			//Other Methods
			new Commands(this, f, caoAPI, menu);
			new TabComplete(this, f);
			new Info();
			updateChecker();
		} else {
			getLogger().warning("CasieAttractionOperate not found! Disabling plugin.");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
		}
	}
	
	@Override
	public void onDisable() {
		saveDefaultConfig();
		reloadConfig();
	}
	
	public boolean papiPresent() {
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {return true;
		} else {return false;}
	}

	public API getCAOapi() {
		cao = (me.casiebarie.casieattractionoperate.Main) getServer().getPluginManager().getPlugin("CasieAttractionOperate");
		return cao.api;
	}

	public void updateChecker() {
		new UpdateChecker(this, 1).getVersion(version -> {
			if(this.getDescription().getVersion().equalsIgnoreCase(version)) {
				getLogger().info("You are using the most recent version. (v" + this.getDescription().getVersion() + ")");
			} else {
				getLogger().info("There is a new update available (v" + version + ")!   You are using: v" + this.getDescription().getVersion());
			}
		});
	}
}
