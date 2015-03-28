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
import org.bukkit.util.Vector;

/**
 *
 * @author chrisbot
 */
public class CommandPos1 implements CommandExecutor {

    private final Boomerang plugin;

    public CommandPos1(Boomerang plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender == plugin.getServer().getConsoleSender()) {
            sender.sendMessage(ChatColor.RED + "ERROR: pos1 cannot be called from console.");
            return true;
        }
        if ((args.length != 0) && (args.length != 3)) {
            sender.sendMessage(ChatColor.RED + "/pos1 accepts three optional arguments.");
            return false;
        }

        Player player = (Player)sender;
        Vector vector;
        
        if (args.length == 0) {
            vector = player.getLocation().toVector().subtract(new Vector(0, 1, 0));
        } else {
            try {
                Double x = Double.parseDouble(args[0]);
                Double y = Double.parseDouble(args[1]);
                Double z = Double.parseDouble(args[2]);
                vector = new Vector(x, y, z);
            } catch (NumberFormatException ex) {
                player.sendMessage(ChatColor.RED + "ERROR: Could not parse location from command line.");
                return true;
            }
        } 
        plugin.getSettings().setPosSelection1(player, vector);
        player.sendMessage(ChatColor.YELLOW + "Position 1 set.");

        return true;
    }
}
