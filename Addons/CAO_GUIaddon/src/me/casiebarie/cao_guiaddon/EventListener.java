package me.casiebarie.cao_guiaddon;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.casiebarie.casieattractionoperate.API;

public class EventListener implements Listener {
	private Functions f;
	private API caoAPI;
	private Menu menu;
	private static FileConfiguration CAOconfig;
	private static FileConfiguration config;
	
	public EventListener(Main plugin, Functions f, API caoAPI, Menu menu) {
		this.f = f;
		this.caoAPI = caoAPI;
		this.menu = menu;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		config = f.getConfig();
		CAOconfig = f.getCAOconfig();
		Player player = (Player) e.getWhoClicked();
		boolean closeOnClick = config.getBoolean(".MenuSettings.CloseOnClick");
		if(f.getINVopen().contains(player.getUniqueId().toString())) {
			for(String attraction : CAOconfig.getConfigurationSection(".Attractions").getKeys(false)) {
				String configTitle = f.Color(f.setPlaceholders(player, config.getString(".MenuSettings.Title").replaceAll("%attraction%", attraction)));
				if(e.getView().getTitle().equals(configTitle)) {
					e.setCancelled(true);
					ItemStack item = e.getCurrentItem();
					if(item == null) {continue;}
					if(!item.hasItemMeta()) {continue;}
					ItemMeta itemMeta = item.getItemMeta();
					String[] localNameSplit = itemMeta.getLocalizedName().split("_");
					if(!localNameSplit[0].equals("CAOmenu")) {continue;}
					player = (config.getBoolean(".MenuSettings.MessageOnClick")) ? player : null;
					//Restraints
					if(localNameSplit[1].equals("Restraints")) {caoAPI.restraints(player, attraction);}
					//Gates
					if(localNameSplit[1].equals("Gates")) {caoAPI.gates(player, attraction);}
					//Release
					if(localNameSplit[1].equals("Release")) {caoAPI.release(player, attraction);}
					//Power
					if(localNameSplit[1].equals("Power")) {caoAPI.power(player, attraction, "TOGGLE");}
					if(closeOnClick) {player.closeInventory();
					} else {menu.updateMenu(player, e.getClickedInventory(), attraction);}
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		Player player = (Player) e.getPlayer();
		if(f.getINVopen().contains(player.getUniqueId().toString())) {
			f.setINVclosed(player.getUniqueId());
		}
	}
	
	public void onPlayerLeave(PlayerQuitEvent e) {
		Player player = (Player) e.getPlayer();
		if(f.getINVopen().contains(player.getUniqueId().toString())) {
			f.setINVclosed(player.getUniqueId());
		}
	}
}
