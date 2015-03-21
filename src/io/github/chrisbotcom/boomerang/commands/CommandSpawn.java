/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.commands;

import io.github.chrisbotcom.boomerang.Boomerang;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author chrisbot
 */
public class CommandSpawn implements CommandExecutor {
    private final Boomerang plugin;
    
    public CommandSpawn(Boomerang plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender == plugin.getServer().getConsoleSender()) {
            sender.sendMessage(ChatColor.RED + "ERROR: spawn cannot be called from console.");
            return true;
        }
        if (args.length > 0) {
            sender.sendMessage(ChatColor.RED + "/spawn does not accept arguments.");
            return false;
        } 
        
        ((Player)sender).setFallDistance(0);
        ((Player)sender).teleport(plugin.getSettings().getSpawn());
        
        sender.sendMessage(ChatColor.YELLOW + "Woosh...");
        
        return true;
    }
}
