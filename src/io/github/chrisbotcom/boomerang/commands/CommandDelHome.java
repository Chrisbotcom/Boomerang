/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.commands;

import io.github.chrisbotcom.boomerang.Boomerang;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        Set<String> set = plugin.getSettings().getHomes(player);

        if (args.length == 0) {
            if (set != null) {
                player.sendMessage(String.format(ChatColor.YELLOW + "Home(s): %s", set.toString().replaceAll("[\\[\\]]", "")));
            } else {
                player.sendMessage(ChatColor.YELLOW + "You have no homes set. To set a home type /sethome.");
            }
        } else {
            String home = args[0].toLowerCase();
            if (set.contains(home)) {
                plugin.getSettings().removeHome(player, home);
                player.sendMessage(ChatColor.YELLOW + "Home '" + home + " has been removed.");
            } else {
                player.sendMessage(ChatColor.YELLOW + "Home '" + home + "' not found. To set a home, type " + ChatColor.ITALIC + "/sethome.");
            }
        }
        
        return true;
    }
}
