/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.commands;

import io.github.chrisbotcom.boomerang.Boomerang;
import io.github.chrisbotcom.boomerang.commands.tabcomplete.HomeTabComplete;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author chrisbot
 */
public class CommandHome implements CommandExecutor {

    private final Boomerang plugin;

    public CommandHome(Boomerang plugin) {
        this.plugin = plugin;
        plugin.getCommand("home").setTabCompleter(new HomeTabComplete(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender == plugin.getServer().getConsoleSender()) {
            sender.sendMessage(ChatColor.RED + "ERROR: home cannot be called from console.");
            return true;
        }
        if (args.length > 1) {
            sender.sendMessage(ChatColor.RED + "/home accepts one optional argument.");
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            Set<String> set = plugin.getSettings().getHomes(player);
            if (set != null) {
                player.sendMessage(String.format(ChatColor.YELLOW + "Home(s): %s", set.toString().replaceAll("[\\[\\]]", "")));
            } else {
                player.sendMessage(ChatColor.YELLOW + "You have no homes set. To set a home type /sethome.");
            }
        } else {
            String home = args[0].toLowerCase();
            Location location = plugin.getSettings().getHome(player, home);
            if (location != null) {
                player.setFallDistance(0);
                player.teleport(location);
                player.sendMessage(ChatColor.YELLOW + "Woosh...");
            } else {
                player.sendMessage(ChatColor.YELLOW + "Home '" + home + "' not found. To set a home, type " + ChatColor.ITALIC + "/sethome.");
            }
        }

        return true;
    }
}
