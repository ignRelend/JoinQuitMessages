package me.davey.joinquitmessages;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        if(!getServer().getPluginManager().getPlugin("PlaceholderAPI").isEnabled()) {
            System.out.println("PlaceholderAPI is not found! You cannot use PlaceholderAPI placeholders until you add the plugin!");
        }
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        System.out.println("PlaceholderAPI found! You can use PlaceholderAPI placeholders in messages!");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(getConfig().getString("join-message") != null) {
            event.setJoinMessage(PlaceholderAPI.setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', getConfig().getString("join-message")))
                    .replaceAll("\\{PLAYER}", player.getName())
                    .replaceAll("\\{WORLD}", player.getWorld().getName())
                    .replaceAll("\\{ONLINE}", String.valueOf(player.getServer().getOnlinePlayers().size())));
        } else {
            event.setJoinMessage(null);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(getConfig().getString("quit-message") != null) {
            event.setQuitMessage(PlaceholderAPI.setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', getConfig().getString("quit-message")))
                    .replaceAll("\\{PLAYER}", player.getName())
                    .replaceAll("\\{WORLD}", player.getWorld().getName())
                    .replaceAll("\\{ONLINE}", String.valueOf(player.getServer().getOnlinePlayers().size() - 1)));
        } else {
            event.setQuitMessage(null);
        }
    }

}
