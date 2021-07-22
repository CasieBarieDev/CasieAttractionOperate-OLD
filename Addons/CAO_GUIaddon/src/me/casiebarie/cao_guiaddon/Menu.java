package me.casiebarie.cao_guiaddon;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.casiebarie.casieattractionoperate.API;

public class Menu {
	private Main plugin;
	private Functions f;
	private API caoAPI;
	private static FileConfiguration CAOconfig;
	private static FileConfiguration config;
	private static ArrayList<ItemStack> items = new ArrayList<>();
	BukkitTask task;
	
	public Menu(Main plugin, Functions f, API caoAPI) {
		this.plugin = plugin;
		this.f = f;
		this.caoAPI = caoAPI;
		refreshMenu();
	}
	
	public void openMenu(Player player, String attractionName) {
		config = f.getConfig();
		CAOconfig = f.getCAOconfig();
		String guiName = f.Color(config.getString(".MenuSettings.Title").replaceAll("%attraction%", attractionName));
		Inventory gui = Bukkit.createInventory(player, config.getInt(".MenuSettings.Size"), guiName);
		configureItems();
		updateMenu(player, gui, attractionName);
		f.setINVopen(player.getUniqueId());
		player.openInventory(gui);
	}
	
	public void updateMenu(Player player, Inventory gui, String attractionName) {
		config = f.getConfig();
		CAOconfig = f.getCAOconfig();
		configureItems();
		File aFile1 = new File(Bukkit.getPluginManager().getPlugin("CasieAttractionOperate").getDataFolder() + "/" + CAOconfig.getString(".Attractions." + attractionName));
		FileConfiguration aFile = YamlConfiguration.loadConfiguration(aFile1);
		if(!aFile1.exists()) {return;}
		//Restraints & Gates
		for(int i = 0; i < 6; i++) {
			String _mode_ = null;
			String mode = null;
			int itemNumber = 0;
			mode = (i >= 0 && i <= 2) ? "Restraints" : (i >= 3 && i <= 5) ? "Gates" : null;
			if(aFile.getBoolean(".Status.STATION") == true && aFile.getString(".Status.POWER").equals("Enabled")) {
				if(aFile.getBoolean(".Status." + mode.toUpperCase()) == true) {_mode_ = "Open"; itemNumber = (mode == "Restraints") ? 0 : 3;
				} else {_mode_ = "Closed"; itemNumber = (mode == "Restraints") ? 1 : 4;}
			} else {_mode_ = "NotAllowed"; itemNumber = (mode == "Restraints") ? 2 : 5;}
			if(config.getBoolean(".MenuSettings.Items." + mode + ".ItemEnabled")) {
				ItemStack itemStack = items.get(itemNumber);
				ItemMeta itemMeta = itemStack.getItemMeta();
				//NAME
				String itemName = f.Color(config.getString(".MenuSettings.Items." + mode + "." + _mode_ + ".Name").replaceAll("%attraction", attractionName));
				itemMeta.setDisplayName(f.setPlaceholders(player, itemName));
				//LORE
				ArrayList<String> itemLoreList = new ArrayList<>();
				for(String configItemLore : config.getStringList(".MenuSettings.Items." + mode + "." + _mode_ + ".Lore")) {
					String itemLore = f.Color(configItemLore.replaceAll("%attraction%", attractionName));
					itemLoreList.add(f.setPlaceholders(player, itemLore));
				}
				if(itemLoreList != null) {itemMeta.setLore(itemLoreList);}
				//LOCALIZED NAME
				itemMeta.setLocalizedName("CAOmenu_" + mode);
				//SET ITEMMETA
				itemStack.setItemMeta(itemMeta);
				gui.setItem(config.getInt(".MenuSettings.Items." + mode + ".Slot"), itemStack);
			}
		}
		//Release
		if(config.getBoolean(".MenuSettings.Items.Release.ItemEnabled")) {
			String _mode_ = null;
			int itemNumber = 0;
			if(aFile.getBoolean(".Status.RELEASE") == true) {_mode_ = "Allowed"; itemNumber = 6;
			} else {_mode_ = "Disallowed"; itemNumber = 7;}
			ItemStack itemStack = items.get(itemNumber);
			ItemMeta itemMeta = itemStack.getItemMeta();
			//NAME
			String itemName = f.Color(config.getString(".MenuSettings.Items.Release." + _mode_ + ".Name").replaceAll("%attraction", attractionName));
			itemMeta.setDisplayName(f.setPlaceholders(player, itemName));
			//LORE
			ArrayList<String> itemLoreList = new ArrayList<>();
			for(String configItemLore : config.getStringList(".MenuSettings.Items.Release." + _mode_ + ".Lore")) {
				String itemLore = f.Color(configItemLore.replaceAll("%attraction%", attractionName));
				itemLoreList.add(f.setPlaceholders(player, itemLore));
			}
			if(itemLoreList != null) {itemMeta.setLore(itemLoreList);}
			//LOCALIZED NAME
			itemMeta.setLocalizedName("CAOmenu_Release");
			//SET ITEMMETA
			itemStack.setItemMeta(itemMeta);
			gui.setItem(config.getInt(".MenuSettings.Items.Release.Slot"), itemStack);
		}
		//POWER
		if(config.getBoolean(".MenuSettings.Items.Power.ItemEnabled")) {
			String _mode_ = null;
			int itemNumber = 0;
			if(aFile.getString(".Status.POWER").equals("Enabled")) {_mode_ = "Enabled"; itemNumber = 8;}
			else if (aFile.getString(".Status.POWER").equals("Disabled")) {_mode_ = "Disabled"; itemNumber = 9;}
			else if (aFile.getString(".Status.POWER").equals("Startup")) {_mode_ = "Startup"; itemNumber = 10;}
			else if (aFile.getString(".Status.POWER").equals("Shutdown")){_mode_ = "Shutdown"; itemNumber = 11;}
			ItemStack itemStack = items.get(itemNumber);
			ItemMeta itemMeta = itemStack.getItemMeta();
			//NAME
			String itemName = f.Color(config.getString(".MenuSettings.Items.Power." + _mode_ + ".Name").replaceAll("%attraction", attractionName));
			itemMeta.setDisplayName(f.setPlaceholders(player, itemName));
			//LORE
			ArrayList<String> itemLoreList = new ArrayList<>();
			for(String configItemLore : config.getStringList(".MenuSettings.Items.Power." + _mode_ + ".Lore")) {
				String itemLore = f.Color(configItemLore.replaceAll("%attraction", attractionName));
				itemLoreList.add(f.setPlaceholders(player, itemLore));
			}
			if(itemLoreList != null) {itemMeta.setLore(itemLoreList);}
			//LOCALIZED NAME
			itemMeta.setLocalizedName("CAOmenu_Power");
			//SET ITEMMETA
			itemStack.setItemMeta(itemMeta);
			gui.setItem(config.getInt(".MenuSettings.Items.Power.Slot"), itemStack);
		}
	}
	
	public void restartRefresh() {
		task.cancel();
		ArrayList<String> uuidList = new ArrayList<>();
		for(String uUIDname : f.getINVopen()) {uuidList.add(uUIDname);}
		for(String uuidname : uuidList) {
			Player player = Bukkit.getPlayer(UUID.fromString(uuidname));
			player.closeInventory();
			f.sendMessage(player, "ClosedMenu", "");
		}
		refreshMenu();
	}

	public void refreshMenu() {
		CAOconfig = f.getCAOconfig();
		config = f.getConfig();
		long period = config.getLong(".MenuSettings.UpdateInterval");
		if(period == 0) {return;}
		task = new BukkitRunnable() {
			@Override
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					if(player.getOpenInventory() == null) {continue;}
					for(String attractionName : CAOconfig.getConfigurationSection(".Attractions.").getKeys(false)) {
						String invName = f.Color(f.setPlaceholders(player, config.getString(".MenuSettings.Title").replaceAll("%attraction%", attractionName)));
						if(player.getOpenInventory().getTitle().equals(invName)) {updateMenu(player, player.getOpenInventory().getTopInventory(), attractionName);}
					}
				}
			}
		}.runTaskTimer(plugin, 0L, period);
	}
	
	@SuppressWarnings("deprecation")
	private void configureItems() {
		items = new ArrayList<>();
		for(int i = 0; i < 12; i++) {
			String _mode_ = null;
			String mode = null;
			_mode_ = (i == 0 || i == 3) ? "Open" : (i == 1 || i == 4) ? "Closed" : (i == 2 || i == 5) ? "NotAllowed" : (i == 6) ? "Allowed" : (i == 7) ? "Disallowed" : (i == 8) ? "Enabled" : (i == 9) ? "Disabled" : (i == 10) ? "Startup" : (i == 11) ? "Shutdown" : null;
			mode = (i >= 0 && i <= 2) ? "Restraints" : (i >= 3 && i <= 5) ? "Gates" : (i >= 6 && i <= 7) ? "Release" : (i >= 8 && i <= 11) ? "Power" : null;
			if(config.getBoolean(".MenuSettings.Items." + mode + ".ItemEnabled")) {
				if(caoAPI.isLegacy()) {
					String[] configItem = config.getString(".MenuSettings.Items." + mode + "." + _mode_ + ".Item").split(":");
					try {
						Material material = Material.matchMaterial(configItem[0].toUpperCase());
						byte data = 0;
						if(configItem.length == 2) {
							try {data = Byte.parseByte(configItem[1]);
							} catch (NumberFormatException e) {plugin.getLogger().warning("The data number of " + config.getString(".MenuSettings.Items." + mode + "." + _mode_ + ".Item") + " at " + mode + " " + _mode_ + " is wrong!");}
						}
						items.add(i, new ItemStack(material, 1, (short) 0, data));
					} catch (Exception e) {plugin.getLogger().warning("The Item " + config.getString(".MenuSettings.Items." + mode + "." + _mode_ + ".Item") + " at " + mode + " " + _mode_ + " is not recognized!");}
				} else {items.add(i, new ItemStack(Material.matchMaterial(config.getString(".MenuSettings.Items." + mode + "." + _mode_ + ".Item"))));}
			} else {items.add(i, new ItemStack(Material.AIR));}
		}
	}
}
