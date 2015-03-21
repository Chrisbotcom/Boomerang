/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.commands;

import io.github.chrisbotcom.boomerang.Boomerang;
import io.github.chrisbotcom.boomerang.commands.tprequest.RequestType;
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
public class CommandTPAccept implements CommandExecutor {

    private final Boomerang plugin;

    public CommandTPAccept(Boomerang plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender == plugin.getServer().getConsoleSender()) {
            sender.sendMessage(ChatColor.RED + "ERROR: tpaccept cannot be called from console.");
            return true;
        }
        if (args.length > 1) {
            sender.sendMessage(ChatColor.RED + "/tpaccept accepts one optional player name.");
            return false;
        }
        
        Player recipient = (Player)sender;
        List<Player> list = plugin.getTpRequests().getRequestsPendingFor(recipient);
        
        if (list.isEmpty()) {
            sender.sendMessage(ChatColor.RED + "You have no pending teleport requests.");
            return true;
        }
        
        if (args.length == 0 && list.size() > 1) {
            sender.sendMessage(ChatColor.RED + "You have more than one pending teleport request. Please, choose one.");
            sender.sendMessage(ChatColor.YELLOW + "Pending requests: " + list.toString().replaceAll("[\\[\\]]", ""));
            return true;
        }
        
        Player requester;
        
        if (args.length == 1) {
            requester = plugin.getServer().getPlayer(args[0]);
            if (requester == null) {
                sender.sendMessage(ChatColor.RED + "Player " + args[0] + " not found.");
                return true;
            } else if (requester.getUniqueId() == recipient.getUniqueId()) {
                sender.sendMessage(ChatColor.RED + "Perhaps you are beside yourself.");
                return true;
            } else if (!list.contains(requester)) {
                sender.sendMessage(ChatColor.RED + "You do not have a pending request from " + args[0] + ".");
                return true;
            }
        } else {
            requester = list.get(0);
        }
        
        if (plugin.getTpRequests().hasRequestPendingFrom(recipient, requester)) {
            RequestType requestType = plugin.getTpRequests().removeRequestTypeForFrom(recipient, requester);
            plugin.getTpRequests().removeRequestForFrom(recipient, requester);
            sender.sendMessage(ChatColor.YELLOW + "Woosh...");
            requester.sendMessage(ChatColor.YELLOW + sender.getName() + " has accepted your teleport request.");
            if (requestType == RequestType.TPR) {
                requester.teleport(recipient);
            } else {
                recipient.teleport(requester);
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You don't have a pending teleport request from " + requester.getName() + ".");
        }

        return true;
    }
}
