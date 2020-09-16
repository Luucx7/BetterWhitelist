package me.luucx7.whitelist.bungee.listeners;

import java.util.Optional;

import me.luucx7.whitelist.bungee.BetterWhitelistBungee;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LoginListener implements Listener {
	
	@EventHandler
	public void login(PostLoginEvent ev) {
		Optional<String> playerOp = BetterWhitelistBungee.config.getStringList("permitidos").stream().filter(s -> ev.getPlayer().getName().equalsIgnoreCase(s)).findFirst();
		
		if (!playerOp.isPresent()) {
			ev.getPlayer().disconnect(new TextComponent(BetterWhitelistBungee.config.getString("whitelist.mensagem").replace("&", "§")));
		}
		return;
	}
}
