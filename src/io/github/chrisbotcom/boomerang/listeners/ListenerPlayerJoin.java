/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.listeners;

import io.github.chrisbotcom.boomerang.Boomerang;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Team;

/**
 *
 * @author chrisbot
 */
public class ListenerPlayerJoin implements Listener {

    private final Boomerang plugin;

    public ListenerPlayerJoin(Boomerang plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        String commands_file;
        if (player.getLastPlayed() == 0) { // new player
            if (plugin.getSettings().isSpawnSet()) {
                player.teleport(plugin.getSettings().getSpawn());
            }
            commands_file = "commands_new_join.txt";
        } else {
            commands_file = "commands_join.txt";
        }

        try {
            File dataFolder = this.plugin.getDataFolder();
            File f = new File(dataFolder, commands_file);
            Server server = player.getServer();
            Scanner s = new Scanner(f);
            String line;
            while (s.hasNext()) {
                line = s.nextLine().replace("@p", player.getName());
                server.dispatchCommand(server.getConsoleSender(), line);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ListenerPlayerJoin.class.getName()).log(Level.SEVERE, null, ex);
        }

        plugin.getSettings().loadPlayerHomes(player);
        
        String prefix = "";
        String suffix = "";
        Team team = player.getScoreboard().getPlayerTeam(player);
        if (team != null) {
            prefix = team.getPrefix();
            suffix = team.getSuffix();
        }
        e.setJoinMessage(ChatColor.YELLOW + "Welcome " + prefix + player.getDisplayName() + suffix + ChatColor.YELLOW + " to the game!");
    }
}
