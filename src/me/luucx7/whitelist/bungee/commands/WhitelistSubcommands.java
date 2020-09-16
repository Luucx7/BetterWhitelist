package me.luucx7.whitelist.bungee.commands;

import me.luucx7.whitelist.bungee.BetterWhitelistBungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class WhitelistSubcommands {

	CommandSender sender;
	String[] args;

	public WhitelistSubcommands(CommandSender sender, String[] args) {
		this.sender = sender;
		this.args = args;

		if (args.length==0) {
			help();
			return;
		}

		switch (args[0]) {
		case "add":
		case "adicionar":
			add();
			return;
		case "remove":
		case "remover":
			remove();
			return;
		case "on":
		case "ligar":
		case "habilitar":
			on();
			return;
		case "off":
		case "desligar":
		case "desativar":
			off();
			return;
		case "checar":
		case "check":
		case "verificar":
			check();
			return;
		case "list":
		case "listar":
			list();
			return;
		case "reload":
		case "recarregar":
			reload();
			return;
		default:
			help();
			return;
		}
	}

	public void list() {
		String lista = "";
		int size = BetterWhitelistBungee.permitidos.size();
		for (int i=0;i<size;i++) {
			lista = lista+BetterWhitelistBungee.permitidos.get(i);
			if (i!=size-1) {
				lista = lista+", ";
			}
		}

		sender.sendMessage(new ComponentBuilder("§8§lJogadores na whitelist: §r§8("+BetterWhitelistBungee.permitidos.size()+")").create());
		sender.sendMessage(new ComponentBuilder(lista).color(ChatColor.GRAY).create());
	}

	public void check() {
		if (args.length<2) {
			sender.sendMessage(new ComponentBuilder("Argumentos inválidos").color(ChatColor.RED).create());
			return;
		}

		if (checklist()) {
			sender.sendMessage(new ComponentBuilder("O jogador "+args[1]+" está na lista branca!").color(ChatColor.GREEN).create());
			return;
		}
		sender.sendMessage(new ComponentBuilder("O jogador "+args[1]+" não está na lista branca!").color(ChatColor.RED).create());
	}

	private boolean checklist() {
		int size = BetterWhitelistBungee.permitidos.size();
		for (int i=0;i<size;i++) {
			if (BetterWhitelistBungee.permitidos.get(i).equalsIgnoreCase(args[1])) {
				return true;
			}
		}
		return false;
	}

	public void add() {
		if (args.length<2) {
			sender.sendMessage(new ComponentBuilder("Argumentos inválidos").color(ChatColor.RED).create());
			return;
		}
		
		if (args.length>2) {
			for (int i=1;i<args.length;i++) {
				if (!BetterWhitelistBungee.permitidos.contains(args[i])) BetterWhitelistBungee.permitidos.add(args[i]);
			}
			BetterWhitelistBungee.config.set("permitidos", BetterWhitelistBungee.permitidos);
			BetterWhitelistBungee.saveConfig();
			sender.sendMessage(new ComponentBuilder("Jogadores adicionados a lista branca.").color(ChatColor.GREEN).create());
			return;
		}
		
		if (!BetterWhitelistBungee.permitidos.contains(args[1])) BetterWhitelistBungee.permitidos.add(args[1]);
		BetterWhitelistBungee.config.set("permitidos", BetterWhitelistBungee.permitidos);
		BetterWhitelistBungee.saveConfig();
		sender.sendMessage(new ComponentBuilder("Jogador "+args[1]+" adicionado a lista branca.").color(ChatColor.GREEN).create());
	}

	public void remove() {
		if (args.length<2) {
			sender.sendMessage(new ComponentBuilder("Argumentos inválidos").color(ChatColor.RED).create());
			return;
		}

		if (args.length>2) {
			for (int i=1;i<args.length;i++) {
				BetterWhitelistBungee.permitidos.remove(args[i]);
			}
			BetterWhitelistBungee.config.set("permitidos", BetterWhitelistBungee.permitidos);
			BetterWhitelistBungee.saveConfig();
			sender.sendMessage(new ComponentBuilder("Jogadores removidos a lista branca.").color(ChatColor.RED).create());
			return;
		}
		
		BetterWhitelistBungee.permitidos.remove(args[1]);
		BetterWhitelistBungee.config.set("permitidos", BetterWhitelistBungee.permitidos);
		BetterWhitelistBungee.saveConfig();
		sender.sendMessage(new ComponentBuilder("Jogador "+args[1]+" removido da lista branca.").color(ChatColor.RED).create());
	}

	public void on() {
		if (BetterWhitelistBungee.whitelist) {
			sender.sendMessage(new ComponentBuilder("A lista branca já está ligada!").color(ChatColor.RED).create());
			return;
		}

		BetterWhitelistBungee.whitelist = true;
		BetterWhitelistBungee.config.set("whitelist.habilitar", true);
		BetterWhitelistBungee.saveConfig();
		sender.sendMessage(new ComponentBuilder("A lista branca foi habilitada!").color(ChatColor.GREEN).create());
	}

	public void off() {
		if (!BetterWhitelistBungee.whitelist) {
			sender.sendMessage(new ComponentBuilder("A lista branca já está desligada!").color(ChatColor.RED).create());
			return;
		}

		BetterWhitelistBungee.whitelist = false;
		BetterWhitelistBungee.config.set("whitelist.habilitar", false);
		BetterWhitelistBungee.saveConfig();
		sender.sendMessage(new ComponentBuilder("A lista branca foi desabilitada!").color(ChatColor.RED).create());
	}

	public void reload() {
		BetterWhitelistBungee.instance.getProxy().getScheduler().runAsync(BetterWhitelistBungee.instance, () -> {
			BetterWhitelistBungee.loadConfig();
			sender.sendMessage(new ComponentBuilder("Configuração recarregada.").color(ChatColor.DARK_GREEN).create());
			return;
		});
	}

	public void help() {
		sender.sendMessage(new ComponentBuilder("§8§lBetterWhitelist "+estado()).create());
		sender.sendMessage(new ComponentBuilder(" ").create());
		sender.sendMessage(new ComponentBuilder("/bwhitelist adicionar <Jogador>").color(ChatColor.GRAY).create());
		sender.sendMessage(new ComponentBuilder("/bwhitelist remover <Jogador>").color(ChatColor.GRAY).create());
		sender.sendMessage(new ComponentBuilder("/bwhitelist checar <Jogador>").color(ChatColor.GRAY).create());
		sender.sendMessage(new ComponentBuilder("/bwhitelist on").color(ChatColor.GRAY).create());
		sender.sendMessage(new ComponentBuilder("/bwhitelist off").color(ChatColor.GRAY).create());
		sender.sendMessage(new ComponentBuilder("/bwhitelist listar").color(ChatColor.GRAY).create());
		sender.sendMessage(new ComponentBuilder("/bwhitelist reload").color(ChatColor.GRAY).create());
		return;
	}
	
	private String estado() {
		if (BetterWhitelistBungee.whitelist) return ChatColor.DARK_GREEN+"Ativada";
		return ChatColor.DARK_RED+"Desativada";
	}
}
