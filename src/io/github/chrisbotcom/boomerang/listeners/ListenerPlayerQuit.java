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
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Team;

/**
 *
 * @author chrisbot
 */
public class ListenerPlayerQuit implements Listener {

    private final Boomerang plugin;

    public ListenerPlayerQuit(Boomerang plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        plugin.getSettings().removePlayerHomes(player);
        
        String prefix = "";
        String suffix = "";
        Team team = player.getScoreboard().getPlayerTeam(player);
        if (team != null) {
            prefix = team.getPrefix();
            suffix = team.getSuffix();
        }
        e.setQuitMessage(prefix + player.getDisplayName() + suffix + ChatColor.YELLOW + " has left the game.");
    }
}
