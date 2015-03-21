/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.listeners;

import io.github.chrisbotcom.boomerang.Boomerang;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scoreboard.Team;

/**
 *
 * @author chrisbot
 */
public class ListenerPlayerChat implements Listener {
    private final Boomerang plugin;

    public ListenerPlayerChat(Boomerang plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        
        if (this.plugin.getSettings().getMuteList().contains(player)) {
            e.setCancelled(true);
        }
        
        String message = e.getMessage();
        Team team = player.getScoreboard().getPlayerTeam(player);
        if (team != null) {
            String prefix = team.getPrefix();
            String suffix = team.getSuffix();
            e.setFormat(ChatColor.RESET + "<" + prefix + player.getDisplayName() + suffix + ">" + " " + message);
        }
    }
}
