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
public class CommandTPCancel implements CommandExecutor {

    private final Boomerang plugin;

    public CommandTPCancel(Boomerang plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender == plugin.getServer().getConsoleSender()) {
            sender.sendMessage(ChatColor.RED + "ERROR: tpcancel cannot be called from console.");
            return true;
        }
        if (args.length > 0) {
            sender.sendMessage(ChatColor.RED + "/tpcancel accepts no arguments.");
            return false;
        }
        
        Player requester = (Player)sender;
        
        if (plugin.getTpRequests().hasRequestPending(requester)) {
            plugin.getTpRequests().removeRequestFrom(requester);
            sender.sendMessage(ChatColor.YELLOW + "Teleport request has been removed.");
        } else {
            sender.sendMessage(ChatColor.RED + "You have no pending teleport requests.");
        }
        
        return true;
    }
}
