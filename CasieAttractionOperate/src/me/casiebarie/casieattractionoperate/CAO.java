
package me.casiebarie.casieattractionoperate;

import java.io.File;
import java.io.IOException;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.material.Wool;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

@SuppressWarnings("deprecation")
public class CAO implements CommandExecutor{
	private Main plugin;
	private Functions f;
	private static FileConfiguration config;
	
	public CAO(Main plugin, Functions f) {
		this.plugin = plugin;
		this.f = f;
		plugin.getCommand("CAO").setExecutor(this);
		plugin.getCommand("CAOADMIN").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		config = f.GetConfig();
		String usageMSG = f.getUsageMSG();
		//CAO
		if(cmd.getName().equalsIgnoreCase("CAO")) {
			if(sender.hasPermission("CAO.use") && args.length >= 2 && args.length <= 3) {
				if(!args[0].equals("Sample")) {
					File aFile1 = new File(plugin.getDataFolder() + "/" + config.getString(".Attractions." + args[0]));
					FileConfiguration aFile = YamlConfiguration.loadConfiguration(aFile1);
					String aWorld = aFile.getString(".Settings.World");
					if(aFile1.exists()) {
						if(args[1].toUpperCase().equals(f.GetCmds().get(0).toUpperCase())) {
							restraints(sender, args[0], aFile1, aFile, aWorld);
						} else if (args[1].toUpperCase().equals(f.GetCmds().get(1).toUpperCase())) {
							gates(sender, args[0], aFile1, aFile, aWorld);
						} else if (args[1].toUpperCase().equals(f.GetCmds().get(2).toUpperCase())) {
							release(sender, args[0], aFile1, aFile, aWorld);
						} else if (args[1].toUpperCase().equals(f.GetCmds().get(3).toUpperCase()) && args.length == 3) {
							power(sender, args[0], args[2], aFile1, aFile, aWorld);
						} else if (args[1].toUpperCase().equals(f.GetCmds().get(4).toUpperCase()) && args.length == 3) {
							busy(sender, args[0], args[2], aFile1, aFile, aWorld);
						} else if (args[1].toUpperCase().equals(f.GetCmds().get(5).toUpperCase()) && args.length == 3) {
							station(sender, args[0], args[2], aFile1, aFile, aWorld);
						} else {f.sendMessage(sender, "Usage", args[0], usageMSG);}
					} else {f.sendMessage(sender, "NotInConfig", args[0], "");}
				} else {f.sendMessage(sender, "FeatureDisabled", "", "");}
			} else {
				if (!sender.hasPermission("CAO.use")) {f.sendMessage(sender, "NoPermission", "", "");}
				else {f.sendMessage(sender, "Usage", "<attraction>", usageMSG);}
			}
		}
		//CAOADMIN
		if(cmd.getName().equalsIgnoreCase("CAOADMIN")) {
			if (sender.hasPermission("CAO.admin")) {
				if(args.length == 1 || args.length == 2) {
					if(args[0].toUpperCase().equals("RELOADCONFIG")) {
						f.reloadConfig(sender);
						plugin.LoadSample();
					} else if (args[0].toUpperCase().equals("RELOADATTRACTION") && args.length == 2) {
						File aFile1 = new File(plugin.getDataFolder() + "/" + config.getString(".Attractions." + args[1]));
						FileConfiguration aFile = YamlConfiguration.loadConfiguration(aFile1);
						String aWorld = aFile.getString(".Settings.World");
						if (aFile1.exists()) {
							if(!args[1].equals("Sample")) {
								varControl(aFile1, aFile, aWorld);
								f.sendMessage(sender, "Reload", "", args[1]);
							} else {f.sendMessage(sender, "FeatureDisabled", "", "");}
						} else {f.sendMessage(sender, "NotInConfig", args[1], "");}
					} else {f.sendMessage(sender, "UsageAdmin", "<attraction>", "");}
				} else {f.sendMessage(sender, "UsageAdmin", "<attraction>", "");}
			} else {f.sendMessage(sender, "NoPermission", "", "");}
		} return false;
	}
	
	//Restraints
	public void restraints(CommandSender sender, String aName, File aFile1, FileConfiguration aFile, String aWorld) {
		config = f.GetConfig();
		stationMode(aFile1, aFile);
		if(aFile.getBoolean(".Status.STATION") == true && aFile.getString(".Status.POWER").equals("Enabled")) {
			if(aFile.getBoolean(".Status.RESTRAINTS") == false) {
				SetBlock("Restraints", true, aFile1, aFile, aWorld);
				aFile.set(".Status.RESTRAINTS", true);
				f.sendMessage(sender, "Toggle", aName, f.GetCmds().get(0) + ": " + config.getString(".Variables.Open"));
			} else {
				SetBlock("Restraints", false, aFile1, aFile, aWorld);
				aFile.set(".Status.RESTRAINTS", false);
				f.sendMessage(sender, "Toggle", aName, f.GetCmds().get(0) + ": " + config.getString(".Variables.Closed"));
			}
		} else {f.sendMessage(sender, "NoStationPower", aName, f.GetCmds().get(0));}
		saveAttractionFile(aFile, aFile1);
		varControl(aFile1, aFile, aWorld);
	}
	
	//Gates
	public void gates(CommandSender sender, String aName, File aFile1, FileConfiguration aFile, String aWorld) {
		config = f.GetConfig();
		stationMode(aFile1, aFile);
		if(aFile.getBoolean(".Status.STATION") == true && aFile.getString(".Status.POWER").equals("Enabled")) {
			if(aFile.getBoolean(".Status.GATES") == false) {
				SetBlock("Gates", true, aFile1, aFile, aWorld);
				aFile.set(".Status.GATES", true);
				f.sendMessage(sender, "Toggle", aName, f.GetCmds().get(1) + ": " + config.getString(".Variables.Open"));
			} else {
				SetBlock("Gates", false, aFile1, aFile, aWorld);
				aFile.set(".Status.GATES", false);
				f.sendMessage(sender, "Toggle", aName, f.GetCmds().get(1) + ": " + config.getString(".Variables.Closed"));
			}
		} else {f.sendMessage(sender, "NoStationPower", aName, f.GetCmds().get(1));}
		saveAttractionFile(aFile, aFile1);
		varControl(aFile1, aFile, aWorld);
	}
	
	//Release
	public void release(CommandSender sender, String aName, File aFile1, FileConfiguration aFile, String aWorld) {
		config = f.GetConfig();
		if(aFile.getBoolean(".Status.RELEASE") == true) {
			long releaseTime = aFile.getLong(".Settings.ReleaseTime");
			SetBlock("Release", true, aFile1, aFile, aWorld);
			f.sendMessage(sender, "Toggle", aName, f.GetCmds().get(2));
			aFile.set(".Status.BUSY", true);
			aFile.set(".Status.STATION", false);
			new BukkitRunnable() {
				@Override
				public void run() {
					SetBlock("Release", false, aFile1, aFile, aWorld);
					releaseStatus(aFile1, aFile, aWorld);
				}
			}.runTaskLater(plugin, releaseTime);
		} else {f.sendMessage(sender, "NotPermitted", aName, "");}
		varControl(aFile1, aFile, aWorld);
	}
	
	//Power
	public void power(CommandSender sender, String aName, String mode, File aFile1, FileConfiguration aFile, String aWorld) {
		config = f.GetConfig();
		String usageMSG = f.getUsageMSG();
		if(mode.toUpperCase().equals("TOGGLE") || mode.toUpperCase().equals("DONE")) {
			if(mode.toUpperCase().equals("TOGGLE")) {
				if(aFile.getString(".Status.POWER").equals("Enabled") && aFile.getBoolean(".Status.RELEASE") == true) {
					SetBlock("Power", false, aFile1, aFile, aWorld);
					aFile.set(".Status.POWER", "Shutdown");
					f.sendMessage(sender, "Toggle", aName, f.GetCmds().get(3) + ": " + config.getString(".Variables.PowerShutdown"));
				} else if(aFile.getString(".Status.POWER").equals("Disabled")) {
					SetBlock("Power", true, aFile1, aFile, aWorld);
					aFile.set(".Status.POWER", "Startup");
					f.sendMessage(sender, "Toggle", aName, f.GetCmds().get(3) + ": " + config.getString(".Variables.PowerStartup"));
				} else{f.sendMessage(sender, "NotPermittedPower", aName, "");}
			} else if (mode.toUpperCase().equals("DONE")) {
				if(aFile.getString(".Status.POWER").equals("Startup")) {
					aFile.set(".Status.POWER", "Enabled");
					f.sendMessage(sender, "Toggle", aName, f.GetCmds().get(3) + ": " + config.getString(".Variables.PowerOn"));
				} else if(aFile.getString(".Status.POWER").equals("Shutdown")) {
					aFile.set(".Status.POWER", "Disabled");
					f.sendMessage(sender, "Toggle", aName, f.GetCmds().get(3) + ": " + config.getString(".Variables.PowerOff"));
				}
			}
			saveAttractionFile(aFile, aFile1);
			varControl(aFile1, aFile, aWorld);
		} else {f.sendMessage(sender, "Usage", aName, usageMSG);}
	}
	
	//Busy
	public void busy(CommandSender sender, String aName, String bool, File aFile1, FileConfiguration aFile, String aWorld) {
		config = f.GetConfig();
		if(bool.toUpperCase().equals("TRUE")) {
			aFile.set(".Status.BUSY", true);
			f.sendMessage(sender, "Toggle", aName, f.GetCmds().get(4) + ": " + "&cTRUE&r");
		} else if(bool.toUpperCase().equals("FALSE")) {
			aFile.set(".Status.BUSY", false);
			f.sendMessage(sender, "Toggle", aName, f.GetCmds().get(4) + ": " + "&aFALSE&r");
		} else {f.sendMessage(sender, "Usage", aName, "");}
		saveAttractionFile(aFile, aFile1);
		varControl(aFile1, aFile, aWorld);
	}
	
	//Station
	public void station(CommandSender sender, String aName, String bool, File aFile1, FileConfiguration aFile, String aWorld) {
		config = f.GetConfig();
		if(aFile.getBoolean(".Settings.StationMode") == true) {
			if(bool.toUpperCase().equals("TRUE")) {
				aFile.set(".Status.STATION", true);
				f.sendMessage(sender, "Toggle", aName, f.GetCmds().get(5) + ": " + "&cTRUE&r");
			} else if(bool.toUpperCase().equals("FALSE")) {
				aFile.set(".Status.STATION", false);
				f.sendMessage(sender, "Toggle", aName, f.GetCmds().get(4) + ": " + "&aFALSE&r");
			} else {f.sendMessage(sender, "Usage", aName, "");}
		} else {f.sendMessage(sender, "FeatureDisabled", aName, "");}
		saveAttractionFile(aFile, aFile1);
		varControl(aFile1, aFile, aWorld);
	}
	
	//Var controller
	private void varControl(File aFile1, FileConfiguration aFile, String aWorld) {
		stationMode(aFile1, aFile);
		//Poortjes
		if(f.getVar(aFile1, aFile, aWorld, "GATES").equals(config.getString(".Variables.Closed"))) {
			setSign("Gates", "Closed", aFile1, aFile, aWorld);
		} else if(f.getVar(aFile1, aFile, aWorld, "GATES").equals(config.getString(".Variables.Open"))) {
			setSign("Gates", "Open", aFile1, aFile, aWorld);
		} else if(f.getVar(aFile1, aFile, aWorld, "GATES").equals(config.getString(".Variables.NoClosed"))) {
			setSign("Gates", "NoClosed", aFile1, aFile, aWorld);
		}
		//Beugels
		if(f.getVar(aFile1, aFile, aWorld, "RESTRAINTS").equals(config.getString(".Variables.Closed"))) {
			setSign("Restraints", "Closed", aFile1, aFile, aWorld);
		} else if(f.getVar(aFile1, aFile, aWorld, "RESTRAINTS").equals(config.getString(".Variables.Open"))) {
			setSign("Restraints", "Open", aFile1, aFile, aWorld);
		} else if(f.getVar(aFile1, aFile, aWorld, "RESTRAINTS").equals(config.getString(".Variables.NoClosed"))){
			setSign("Restraints", "NoClosed", aFile1, aFile, aWorld);
		}
		//Vrijgeven
		if(f.getVar(aFile1, aFile, aWorld, "RELEASE").equals(config.getString(".Variables.ReleaseAllowed"))) {
			setSign("Release", "ReleaseAllowed", aFile1, aFile, aWorld);
			aFile.set(".Status.RELEASE", true);
			saveAttractionFile(aFile, aFile1);
		} else if(f.getVar(aFile1, aFile, aWorld, "RELEASE").equals(config.getString(".Variables.ReleaseDisallowed"))){
			setSign("Release", "ReleaseDisallowed", aFile1, aFile, aWorld);
			aFile.set(".Status.RELEASE", false);
			saveAttractionFile(aFile, aFile1);
		}
		//Power
		if(f.getVar(aFile1, aFile, aWorld, "POWER").equals(config.getString(".Variables.PowerOn"))) {
			setSign("Power", "PowerOn", aFile1, aFile, aWorld);
		} else if(f.getVar(aFile1, aFile, aWorld, "POWER").equals(config.getString(".Variables.NoPowerOn"))) {
			setSign("Power", "NoPowerOn", aFile1, aFile, aWorld);
		} else if(f.getVar(aFile1, aFile, aWorld, "POWER").equals(config.getString(".Variables.PowerOff"))) {
			setSign("Power", "PowerOff", aFile1, aFile, aWorld);
		} else if(f.getVar(aFile1, aFile, aWorld, "POWER").equals(config.getString(".Variables.PowerStartup"))) {
			setSign("Power", "PowerStartup", aFile1, aFile, aWorld);
		} else if(f.getVar(aFile1, aFile, aWorld, "POWER").equals(config.getString(".Variables.PowerShutdown"))) {
			setSign("Power", "PowerShutdown", aFile1, aFile, aWorld);
		}
		releaseStatus(aFile1, aFile, aWorld);
	}
	
	//Realease status block
	private void releaseStatus(File aFile1, FileConfiguration aFile, String aWorld) {
		try {
			if(!aFile.getString(".Locations.Status").equals("none")) {
			String[] statusLoc = aFile.getString(".Locations.Status").split(" ");
			Block statusBlock = plugin.getServer().getWorld(aWorld).getBlockAt(Integer.parseInt(statusLoc[0]), Integer.parseInt(statusLoc[1]), Integer.parseInt(statusLoc[2]));
			String[] releaseLoc = aFile.getString(".Locations.Release").split(" ");
			Block releaseBlock = plugin.getServer().getWorld(aWorld).getBlockAt(Integer.parseInt(releaseLoc[0]), Integer.parseInt(releaseLoc[1]), Integer.parseInt(releaseLoc[2]));
			if(releaseBlock.getType() == Material.REDSTONE_BLOCK) {
				if(f.isLegacy()) {
					statusBlock.setType(Material.matchMaterial("WOOL"));
					BlockState blockState = statusBlock.getState();
					blockState.setData(new Wool(DyeColor.LIME));
					blockState.update();
				} else {statusBlock.setType(Material.LIME_WOOL);}
			} else if(aFile.getBoolean(".Status.RELEASE")) {
				if(f.isLegacy()) {
					statusBlock.setType(Material.matchMaterial("WOOL"));
					BlockState blockState = statusBlock.getState();
					blockState.setData(new Wool(DyeColor.ORANGE));
					blockState.update();
				} else {statusBlock.setType(Material.ORANGE_WOOL);}
			} else {
				if(f.isLegacy()) {
					statusBlock.setType(Material.matchMaterial("WOOL"));
					BlockState blockState = statusBlock.getState();
					blockState.setData(new Wool(DyeColor.RED));
					blockState.update();
				} else {statusBlock.setType(Material.RED_WOOL);}
			}
		}
		} catch (NumberFormatException e) {}
	}
	
	//Check stationmode
	private void stationMode(File aFile1, FileConfiguration aFile) {
		if(aFile.getBoolean(".Settings.StationMode") == false && aFile.getBoolean(".Status.BUSY") == true) {
			aFile.set(".Status.STATION", false);
		} else if(aFile.getBoolean(".Settings.StationMode") == false && aFile.getBoolean(".Status.BUSY") == false) {
			aFile.set(".Status.STATION", true);
		}
		saveAttractionFile(aFile, aFile1);
	}
	
	//SetBlock
	private void SetBlock(String mode, boolean blockMode, File aFile1, FileConfiguration aFile, String aWorld) {
		try {
			String[] location = aFile.getString(".Locations." + mode).split(" ");
			Block block = plugin.getServer().getWorld(aWorld).getBlockAt(Integer.parseInt(location[0]), Integer.parseInt(location[1]), Integer.parseInt(location[2]));
			if(f.isLegacy()) {
				if(blockMode) {
					block.setType(Material.REDSTONE_BLOCK);
				} else {
					block.setType(Material.matchMaterial("WOOL"));
					BlockState blockState = block.getState();
					blockState.setData(new Wool(DyeColor.LIME));
					blockState.update();
				}
			} else {
				if(blockMode) {block.setType(Material.REDSTONE_BLOCK);
				} else {block.setType(Material.LIME_WOOL);}
			}
		} catch (NumberFormatException e) {}
	}
	
	//SetSign
	private void setSign(String mode, String var, File aFile1, FileConfiguration aFile, String aWorld) {
		try {
			String[] Location = aFile.getString(".Locations." + mode + "Sign").split(" ");
			Sign sign = (Sign) plugin.getServer().getWorld(aWorld).getBlockAt(Integer.parseInt(Location[0]), Integer.parseInt(Location[1]), Integer.parseInt(Location[2])).getState();
			String signText = ChatColor.translateAlternateColorCodes('&', config.getString(".Variables." + var));
			sign.setLine(aFile.getInt(".Settings.SignLine")-1, signText);
			sign.update();
		} catch (NumberFormatException e) {}
	}
	
	//Save AttractionFile
	private void saveAttractionFile(FileConfiguration aFile, File aFile1) {
		try {aFile.save(aFile1);
		} catch (IOException e) {e.printStackTrace();}
	}
}