/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.commands;

import io.github.chrisbotcom.boomerang.Boomerang;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 *
 * @author chrisbot
 */
public class CommandDelHome implements CommandExecutor {

    private final Boomerang plugin;

    public CommandDelHome(Boomerang plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length > 1) {
            sender.sendMessage(ChatColor.RED + "/delhome accepts one optional argument.");
            return false;
        }

        Player player = (Player) sender;

        try {
            File dataFolder = this.plugin.getDataFolder();
            File homesFolder = new File(dataFolder, "homes");
            File playerHomesFile = new File(homesFolder, player.getUniqueId().toString() + ".yml");

            if (playerHomesFile.exists() && (playerHomesFile.length() > 0)) {
                YamlConfiguration homesConfig = new YamlConfiguration();
                homesConfig.load(playerHomesFile);
                Set<String> homes = homesConfig.getKeys(false);
                if (args.length == 0) {
                    player.sendMessage(String.format(ChatColor.YELLOW + "Home(s): %s", Arrays.toString(homes.toArray()).replaceAll("[\\[\\]]", "")));
                } else {
                    String home = args[0].toLowerCase();
                    if (homes.contains(args[0].toLowerCase())) {
                        homesConfig.set(home, null);
                        homesConfig.save(playerHomesFile);
                        player.sendMessage(ChatColor.YELLOW + "Home '" + home + "' deleted.");
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "Home '" + home + "' not found. To set a home, type " + ChatColor.ITALIC + "/sethome.");
                    }
                }
            } else {
                player.sendMessage(ChatColor.YELLOW + "No homes set. To set a home, type " + ChatColor.ITALIC + "/sethome.");
            }
        } catch (IOException | InvalidConfigurationException ex) {
            Logger.getLogger(CommandDelHome.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }
}
