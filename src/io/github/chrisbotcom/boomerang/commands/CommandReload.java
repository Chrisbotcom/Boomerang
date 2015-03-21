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

/**
 *
 * @author chrisbot
 */
public class CommandReload implements CommandExecutor {
    private final Boomerang plugin;
    
    public CommandReload(Boomerang plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length > 1) {
            sender.sendMessage(ChatColor.RED + "/reload accepts one optional argument.");
            return false;
        }
        if (args.length == 0) {
            plugin.getSettings().loadSpawnConfig();
            sender.sendMessage(ChatColor.YELLOW + "Reloaded all Boomerang configurations.");
        } else {
            if (args[0].equalsIgnoreCase("spawn")) {
                plugin.getSettings().loadSpawnConfig();
                sender.sendMessage(ChatColor.YELLOW + "Reloaded Boomerang spawn configuration.");
            }
        }
        
        return true;
    }
}
