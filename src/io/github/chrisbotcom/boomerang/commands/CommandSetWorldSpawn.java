/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.commands;

import io.github.chrisbotcom.boomerang.Boomerang;
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
public class CommandSetWorldSpawn implements CommandExecutor {
    private final Boomerang plugin;

    public CommandSetWorldSpawn(Boomerang plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender == plugin.getServer().getConsoleSender()) {
            sender.sendMessage(ChatColor.RED + "ERROR: setworldspawn cannot be called from console.");
            return true;
        }
        if (args.length > 0) {
            sender.sendMessage(ChatColor.RED + "/setworldspawn does not accept arguments.");
            return false;
        } 
        Location location = ((Player)sender).getLocation();
        plugin.getSettings().setSpawn(location);
        
        sender.sendMessage(ChatColor.YELLOW + "World spawn set.");
        
        return true;
    }
}
