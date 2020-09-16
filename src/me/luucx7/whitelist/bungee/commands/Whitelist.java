package me.luucx7.whitelist.bungee.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

public class Whitelist extends Command {

	public Whitelist(String name) {
		super(name);
	}

	@Override
	public void execute(CommandSender commandSender, String[] strings) {
		if (!commandSender.hasPermission("bwhitelist.manage")) {
			commandSender.sendMessage(new ComponentBuilder("Você não tem permissão!").color(ChatColor.RED).create());
			return;
		}
		new WhitelistSubcommands(commandSender, strings);
	}
}
