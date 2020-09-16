package me.luucx7.whitelist.bungee;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.LinkedList;

import me.luucx7.whitelist.bungee.commands.Whitelist;
import me.luucx7.whitelist.bungee.listeners.LoginListener;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class BetterWhitelistBungee extends Plugin {

	public static boolean whitelist;
	public static Configuration config;
	public static LinkedList<String> permitidos;
	public static BetterWhitelistBungee instance;

	public void onEnable() {
		instance = this;

		loadConfig();

		getProxy().getPluginManager().registerCommand(this, new Whitelist("bwhitelist"));
		getProxy().getPluginManager().registerCommand(this, new Whitelist("bwhite"));
		this.getProxy().getPluginManager().registerListener(this, new LoginListener());
	}

	public static void saveConfig() {
		instance.getProxy().getScheduler().runAsync(instance, () -> {
			try {
				ConfigurationProvider.getProvider(YamlConfiguration.class).save(BetterWhitelistBungee.config, new File(BetterWhitelistBungee.instance.getDataFolder(), "config.yml"));
			} catch (IOException e) {
				instance.getProxy().getConsole().sendMessage(new TextComponent("Erro ao salvar configuração!"));
				e.printStackTrace();
			}
		});
	}

	public static void loadConfig() {
		try {
			if (!instance.getDataFolder().exists())
				instance.getDataFolder().mkdir();

			File file = new File(instance.getDataFolder(), "config.yml");

			if (!file.exists()) {
				try (InputStream in = instance.getResourceAsStream("config.yml")) {
					Files.copy(in, file.toPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(instance.getDataFolder(), "config.yml"));
		} catch (IOException e) {
			instance.getProxy().getConsole().sendMessage(new TextComponent("Erro ao carregar configuração!"));
			e.printStackTrace();
		}

		permitidos = new LinkedList<String>();
		whitelist = config.getBoolean("whitelist.habilitar");
		try {
			config.getStringList("permitidos").stream().forEach(s -> permitidos.add(s));
		} catch(NullPointerException ex) {
			instance.getProxy().getConsole().sendMessage(new ComponentBuilder("Lista branca vazia!").color(ChatColor.DARK_RED).create());
			return;
		}
	}
}
