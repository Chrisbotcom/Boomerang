/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.commands;

import io.github.chrisbotcom.boomerang.Boomerang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author chrisbot
 */
public class CommandFly implements CommandExecutor {

    private final Boomerang plugin;

    public CommandFly(Boomerang plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length > 1) {
            sender.sendMessage(ChatColor.RED + "/fly accepts one optional argument.");
            return false;
        }

        Player player;

        if (args.length == 0) {
            player = (Player) sender;
            player.setAllowFlight(!player.getAllowFlight());
        } else {
            player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(ChatColor.RED + "Player " + args[0] + " not found.");
            } else {
                player.setAllowFlight(!player.getAllowFlight());
                if (player.getAllowFlight()) {
                    sender.sendMessage(ChatColor.YELLOW + "Player " + args[0] + " is allowed flight.");
                } else {
                    sender.sendMessage(ChatColor.YELLOW + "Flight has been disabled for " + args[0] + ".");
                }
            }
        }

        if (player != null) {
            if (player.getAllowFlight()) {
                player.sendMessage(ChatColor.YELLOW + "Flight has been enabled for you. Double-press SPACE to fly.");
            } else {
                player.sendMessage(ChatColor.YELLOW + "Flight has been disabled for you.");
            }
        }

        return true;
    }
}
