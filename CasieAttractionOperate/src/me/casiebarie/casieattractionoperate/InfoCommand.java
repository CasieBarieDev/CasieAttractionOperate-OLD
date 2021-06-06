package me.casiebarie.casieattractionoperate;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

public class InfoCommand implements CommandExecutor {
	@SuppressWarnings("unused")
	private Main plugin;
	private Functions f;
	public InfoCommand(Main plugin, Functions f) {
		this.plugin = plugin;
		this.f = f;
		plugin.getCommand("CAOINFO").setExecutor((CommandExecutor)this);
	}
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		final ArrayList<String> modes = this.f.GetCmds();
		if (cmd.getName().equalsIgnoreCase("CAOinfo") && sender.hasPermission("CAO.use") && sender instanceof Player) {
			sender.spigot().sendMessage(
				new ComponentBuilder("------------- ").color(ChatColor.GOLD).bold(false).append("CasieAttractionOperate").color(ChatColor.AQUA).bold(true).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("By: ").color(ChatColor.BLUE).append("CasieBarie").color(ChatColor.YELLOW).create())).append(" -------------").color(ChatColor.GOLD).bold(false).event((HoverEvent)null)
				.append("\nSETUP:").color(ChatColor.LIGHT_PURPLE).bold(true).append("\nTo create a new attraction copy the 'Sample' file and rename it to the name of your attraction. Then go into the config file and put the name and the location of the file under 'Attractions:'. Put in your attraction file all the locations of your system. Tweak the settings and youre good to go!").color(ChatColor.DARK_GREEN).bold(false)
				.append("\n\nCOMMANDS:").color(ChatColor.LIGHT_PURPLE).bold(true)
				.append("\n/cao ").color(ChatColor.YELLOW).bold(false).append("<attraction> ").color(ChatColor.GOLD).append((String)modes.get(0)).color(ChatColor.YELLOW).append(" - To open/close the restraints.").color(ChatColor.DARK_AQUA)
				.append("\n/cao ").color(ChatColor.YELLOW).append("<attraction> ").color(ChatColor.GOLD).append((String)modes.get(1)).color(ChatColor.YELLOW).append(" - To open/close the gates.").color(ChatColor.DARK_AQUA)
				.append("\n/cao ").color(ChatColor.YELLOW).append("<attraction> ").color(ChatColor.GOLD).append((String)modes.get(2)).color(ChatColor.YELLOW).append(" - To release/start the attraction.").color(ChatColor.DARK_AQUA)
				.append("\n/cao ").color(ChatColor.YELLOW).append("<attraction> ").color(ChatColor.GOLD).append((String)modes.get(3)).color(ChatColor.YELLOW).append(" - Use 'toggle' to enable/disable the attraction. Use 'done' when your system has finished switching.").color(ChatColor.DARK_AQUA)
				.append("\n/cao ").color(ChatColor.YELLOW).append("<attraction> ").color(ChatColor.GOLD).append((String)modes.get(4)).color(ChatColor.YELLOW).append(" - Set the status of the attraction to busy. You cannot release/start the attraction if busy is true.").color(ChatColor.DARK_AQUA)
				.append("\n/cao ").color(ChatColor.YELLOW).append("<attraction> ").color(ChatColor.GOLD).append((String)modes.get(5)).color(ChatColor.YELLOW).append(" - If the attraction is in the station. Only if this is true you can open/close the restraints/gates. (This is disabled when 'stationMode' is false)").color(ChatColor.DARK_AQUA)
				.append("\n\n/caoadmin ReloadConfig").color(ChatColor.YELLOW).append(" - Reload the config.").color(ChatColor.DARK_AQUA)
				.append("\n/caoadmin ReloadAttraction ").color(ChatColor.YELLOW).append("<attraction>").color(ChatColor.GOLD).append(" - Reload the variables of the attraction.").color(ChatColor.DARK_AQUA)
				.append("\n\nHAVE FUN!").color(ChatColor.GREEN).bold(true)
				.append("--------------------------------------------------").color(ChatColor.GOLD).bold(false).create());
		}
		return false;
	}
}
