/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.listeners;

import io.github.chrisbotcom.boomerang.Boomerang;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

/**
 *
 * @author chrisbot
 */
public class ListenerPlayerChatTabComplete implements Listener {

    private final Boomerang plugin;

    public ListenerPlayerChatTabComplete(Boomerang plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChatTabComplete(PlayerChatTabCompleteEvent e) {
        e.getPlayer().sendMessage(String.format(ChatColor.GRAY + "message: %s", e.getChatMessage()));
        e.getPlayer().sendMessage(String.format(ChatColor.GRAY + "completions: %s", e.getTabCompletions().toString().replaceAll("[\\[\\]]", "")));

        
    }
}
