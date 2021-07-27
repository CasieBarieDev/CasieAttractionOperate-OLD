package me.casiebarie.casieattractionoperate.other;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.casiebarie.casieattractionoperate.Functions;
import me.casiebarie.casieattractionoperate.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;

public class InfoCommand implements CommandExecutor {
	@SuppressWarnings("unused")
	private Main plugin;
	private Functions f;
	public InfoCommand(Main plugin, Functions f) {
		this.plugin = plugin;
		this.f = f;
		plugin.getCommand("CAOINFO").setExecutor((CommandExecutor)this);
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (cmd.getName().equalsIgnoreCase("CAOinfo") && sender.hasPermission("CAO.use") && sender instanceof Player) {
			if(args.length == 0) {f.sendMessage(sender, "UsageInfo", "", "");
			} else if(args.length == 1) {
				if(args[0].toUpperCase().equals("SETUP")) {page1(sender);
				} else if(args[0].toUpperCase().equals("COMMANDS")) {page2(sender);
				} else if(args[0].toUpperCase().equals("PLACEHOLDERS")) {page3(sender);
				} else if(args[0].toUpperCase().equals("SUPPORT")) {page4(sender);  
				} else {f.sendMessage(sender, "UsageInfo", "", "");}
			}
		} else {f.sendMessage(sender, "NoPermission", "", "");}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public void page1(CommandSender sender) {
		sender.spigot().sendMessage(
				new ComponentBuilder("------------- ").color(ChatColor.GOLD).bold(false).append("CasieAttractionOperate").color(ChatColor.AQUA).bold(true).event(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("By: ").color(ChatColor.BLUE).append("CasieBarie").color(ChatColor.YELLOW).create())).append(" -------------").color(ChatColor.GOLD).bold(false).event((HoverEvent)null)
				.append("\nSETUP:").color(ChatColor.LIGHT_PURPLE).bold(true).append("\nTo create a new attraction copy the 'Sample' file and rename it to the name of your attraction. Go into the config file and put the name and location of the file under 'Attractions:'. Put in your attraction file all the locations of your system. Tweak the settings and your good to go!").color(ChatColor.DARK_GREEN).bold(false)
				.append("\n--------------------------------------------------").color(ChatColor.GOLD).bold(false).create());
	}
	@SuppressWarnings("deprecation")
	public void page2(CommandSender sender) {
		final ArrayList<String> modes = this.f.GetCmds();
		sender.spigot().sendMessage(
				new ComponentBuilder("------------- ").color(ChatColor.GOLD).bold(false).append("CasieAttractionOperate").color(ChatColor.AQUA).bold(true).event(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("By: ").color(ChatColor.BLUE).append("CasieBarie").color(ChatColor.YELLOW).create())).append(" -------------").color(ChatColor.GOLD).bold(false).event((HoverEvent)null)
				.append("\nCOMMANDS:").color(ChatColor.LIGHT_PURPLE).bold(true)
				.append("\n/cao ").color(ChatColor.YELLOW).bold(false).append("<attraction> ").color(ChatColor.GOLD).append((String)modes.get(0)).color(ChatColor.YELLOW).append(" - To open/close the restraints.").color(ChatColor.DARK_AQUA)
				.append("\n/cao ").color(ChatColor.YELLOW).append("<attraction> ").color(ChatColor.GOLD).append((String)modes.get(1)).color(ChatColor.YELLOW).append(" - To open/close the gates.").color(ChatColor.DARK_AQUA)
				.append("\n/cao ").color(ChatColor.YELLOW).append("<attraction> ").color(ChatColor.GOLD).append((String)modes.get(2)).color(ChatColor.YELLOW).append(" - To release/start the attraction.").color(ChatColor.DARK_AQUA)
				.append("\n/cao ").color(ChatColor.YELLOW).append("<attraction> ").color(ChatColor.GOLD).append((String)modes.get(3)).color(ChatColor.YELLOW).append(" - Use 'toggle' to enable/disable the attraction. Use 'done' when your system has finished switching.").color(ChatColor.DARK_AQUA)
				.append("\n/cao ").color(ChatColor.YELLOW).append("<attraction> ").color(ChatColor.GOLD).append((String)modes.get(4)).color(ChatColor.YELLOW).append(" - Set the status of the attraction to busy. You cannot release/start the attraction if busy is true.").color(ChatColor.DARK_AQUA)
				.append("\n/cao ").color(ChatColor.YELLOW).append("<attraction> ").color(ChatColor.GOLD).append((String)modes.get(5)).color(ChatColor.YELLOW).append(" - If the attraction is in the station. Only if this is true you can open/close the restraints/gates.").color(ChatColor.DARK_AQUA)
				.append("\n/caoadmin ReloadConfig").color(ChatColor.YELLOW).append(" - Reload the config.").color(ChatColor.DARK_AQUA)
				.append("\n/caoadmin ReloadAttraction ").color(ChatColor.YELLOW).append("<attraction>").color(ChatColor.GOLD).append(" - Reload the variables of the attraction.").color(ChatColor.DARK_AQUA)
				.append("\n--------------------------------------------------").color(ChatColor.GOLD).bold(false).create());
	}
	@SuppressWarnings("deprecation")
	public void page3(CommandSender sender) {
		sender.spigot().sendMessage(
				new ComponentBuilder("------------- ").color(ChatColor.GOLD).bold(false).append("CasieAttractionOperate").color(ChatColor.AQUA).bold(true).event(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("By: ").color(ChatColor.BLUE).append("CasieBarie").color(ChatColor.YELLOW).create())).append(" -------------").color(ChatColor.GOLD).bold(false).event((HoverEvent)null)
				.append("\nPLACEHOLDERS:").color(ChatColor.LIGHT_PURPLE).bold(true)
				.append("\n%cao_<attraction>_<mode>%").color(ChatColor.YELLOW).bold(false).append(" - The mode can be Restraints, Gates, Release, Power or Station. You can also use your custom commands set in the config.").color(ChatColor.DARK_AQUA)
				.append("\n\nAll the custom messages can include PAPI placeholders.").color(ChatColor.DARK_GREEN)
				.append("\n--------------------------------------------------").color(ChatColor.GOLD).bold(false).create());
	}
	@SuppressWarnings("deprecation")
	public void page4(CommandSender sender) {
		sender.spigot().sendMessage(
				new ComponentBuilder("------------- ").color(ChatColor.GOLD).bold(false).append("CasieAttractionOperate").color(ChatColor.AQUA).bold(true).event(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("By: ").color(ChatColor.BLUE).append("CasieBarie").color(ChatColor.YELLOW).create())).append(" -------------").color(ChatColor.GOLD).bold(false).event((HoverEvent)null)
				.append("\nSUPPORT:").color(ChatColor.LIGHT_PURPLE).bold(true)
				.append("\nFind more info on the ").color(ChatColor.DARK_GREEN).bold(false).append("Wiki").color(ChatColor.DARK_PURPLE).underlined(true).event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/CasieBarieDev/CasieAttractionOperate/wiki")).event(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("Click to open the Wiki!").color(ChatColor.YELLOW).create())).append(" or get more support in our ").color(ChatColor.DARK_GREEN).underlined(false).event((ClickEvent)null).event((HoverEvent)null).append("Discord").color(ChatColor.AQUA).underlined(true).event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/ZptCBHeHyg")).event(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("Click to join the Discord server!").color(ChatColor.YELLOW).create())).append("server!").color(ChatColor.DARK_GREEN).underlined(false).event((ClickEvent)null).event((HoverEvent)null)
				.append("\n\nHAVE FUN!").color(ChatColor.GREEN).bold(true)
				.append("\n--------------------------------------------------").color(ChatColor.GOLD).bold(false).create());
	}
}