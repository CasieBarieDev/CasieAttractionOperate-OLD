package me.casiebarie.casieattractionoperate;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignClick implements Listener {
	private Main plugin;
	private Functions f;
	private static FileConfiguration config;
	
	public SignClick(Main plugin, Functions f) {
		this.plugin = plugin;
		this.f = f;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	private enum modes {Restraints, Gates, Release, Power}
	
	@EventHandler
	private void signClick(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		config = f.GetConfig();
		if(e.getClickedBlock() == null) {return;}
		if(e.getClickedBlock().getState() instanceof Sign) {
			for(String attractionName : config.getConfigurationSection(".Attractions").getKeys(true)) {
				File aFile1 = new File(plugin.getDataFolder() + "/" + config.getString(".Attractions." + attractionName));
				FileConfiguration aFile = YamlConfiguration.loadConfiguration(aFile1);
				String aWorld = aFile.getString(".Settings.World");
				Block block = e.getClickedBlock();
				for(modes _mode_ : modes.values()) {
					String mode = _mode_.name();
					try {
						String[] Location = aFile.getString(".Locations." + mode + "Sign").split(" ");
						if(plugin.getServer().getWorld(aWorld).getBlockAt(Integer.parseInt(Location[0]), Integer.parseInt(Location[1]), Integer.parseInt(Location[2])).equals(block)) {
							if(aFile.getBoolean(".Settings.ClickSign") && player.hasPermission("CAO.sign")) {
								e.setCancelled(true);
								if(!aFile.getBoolean(".Settings.ClickSignMessage")) {player = null;}
								if(mode.equals("Restraints")) {
									plugin.api.restraints(player, attractionName);
								} else if(mode.equals("Gates")) {
									plugin.api.gates(player, attractionName);
								} else if(mode.equals("Release")) {
									plugin.api.release(player, attractionName);
								} else if(mode.equals("Power")) {
									plugin.api.power(player, attractionName, "TOGGLE");
								}
							}
						}
					} catch (NumberFormatException | NullPointerException ex) {}
				}
			}
		}
	}
}
