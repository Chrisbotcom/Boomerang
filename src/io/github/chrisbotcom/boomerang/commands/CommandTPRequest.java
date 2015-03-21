/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.commands;

import io.github.chrisbotcom.boomerang.Boomerang;
import io.github.chrisbotcom.boomerang.commands.tprequest.RequestType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author chrisbot
 */
public class CommandTPRequest implements CommandExecutor {

    private final Boomerang plugin;

    public CommandTPRequest(Boomerang plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender == plugin.getServer().getConsoleSender()) {
            sender.sendMessage(ChatColor.RED + "ERROR: tprequest cannot be called from console.");
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "/tprequest requires player name.");
            return false;
        }
        Player recipient = plugin.getServer().getPlayer(args[0]);
        if (recipient == null) {
            sender.sendMessage(ChatColor.RED + "Player " + args[0] + " not found.");
        } else if (recipient.getUniqueId() == ((Player)sender).getUniqueId()) {
            sender.sendMessage(ChatColor.RED + "Wherever you go, there you are.");
        } else {
            boolean b = plugin.getTpRequests().addRequest((Player) sender, recipient, RequestType.TPR);
            if (b) {
                sender.sendMessage(ChatColor.YELLOW + "Your previous request has been canceled.");
            }
            sender.sendMessage(ChatColor.YELLOW + "Your request to teleport to " + recipient.getName() + " has been sent. The request will expire in " + plugin.getSettings().getTpRequest_expire() + " seconds.");
            recipient.sendMessage(ChatColor.YELLOW + sender.getName() + " requests to teleport to you. Type " + ChatColor.ITALIC + "/tpaccept" + ChatColor.RESET + ChatColor.YELLOW + " or " + ChatColor.ITALIC + "/tpdeny" + ChatColor.RESET + ChatColor.YELLOW + ". The request will expire in " + plugin.getSettings().getTpRequest_expire() + " seconds.");
        }

        return true;
    }
}
