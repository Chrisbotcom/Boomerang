/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.commands;

import io.github.chrisbotcom.boomerang.Boomerang;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author chrisbot
 */
public class CommandTPList implements CommandExecutor {

    private final Boomerang plugin;

    public CommandTPList(Boomerang plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender == plugin.getServer().getConsoleSender()) {
            sender.sendMessage(ChatColor.RED + "ERROR: tplist cannot be called from console.");
            return true;
        }
        if (args.length > 1) {
            sender.sendMessage(ChatColor.RED + "/tplist accepts no arguments.");
            return false;
        }
        
        Player recipient = (Player)sender;
        List<Player> list = plugin.getTpRequests().getRequestsPendingFor(recipient);
        
        if (list.isEmpty()) {
            sender.sendMessage(ChatColor.RED + "You have no pending teleport requests.");
            return true;
        }
        
        sender.sendMessage(ChatColor.YELLOW + "Pending requests: " + list.toString().replaceAll("[\\[\\]]", ""));

        return true;
    }
}
