/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.commands;

import io.github.chrisbotcom.boomerang.Boomerang;
import io.github.chrisbotcom.boomerang.types.SelectionType;
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
public class CommandPos implements CommandExecutor {

    private final Boomerang plugin;

    public CommandPos(Boomerang plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender == plugin.getServer().getConsoleSender()) {
            sender.sendMessage(ChatColor.RED + "ERROR: pos cannot be called from console.");
            return true;
        }
        if (args.length > 0) {
            sender.sendMessage(ChatColor.RED + "/pos accepts no arguments.");
            return false;
        }

        Player player = (Player)sender;
        SelectionType selection = plugin.getSettings().getPosSelection(player);
        if (selection == null) {
            sender.sendMessage(ChatColor.YELLOW + "Selection not set. Use /pos1 and /pos2 to set a selection.");
        } else {
            Vector pos1 = plugin.getSettings().getPosSelection(player).getPos1();
            if (pos1 != null) {
                sender.sendMessage(ChatColor.YELLOW + "POS1: " + pos1.toString().replace(",", " ").replace(".0", ""));
            } else {
                sender.sendMessage(ChatColor.YELLOW + "POS1: Not set.");
            }
            Vector pos2 = plugin.getSettings().getPosSelection(player).getPos2();
            if (pos2 != null) {
                sender.sendMessage(ChatColor.YELLOW + "POS2: " + pos2.toString().replace(",", " ").replace(".0", ""));
            } else {
                sender.sendMessage(ChatColor.YELLOW + "POS1: Not set.");
            }
            
            if ((pos1 != null) && (pos2 != null) && (pos1 != pos2)) {
                Vector diff = Vector.getMaximum(pos1, pos2).subtract(Vector.getMinimum(pos1, pos2)).add(new Vector(1,1,1));
                int volume = diff.getBlockX() * diff.getBlockY() * diff.getBlockZ();
                sender.sendMessage(ChatColor.YELLOW + "Total Blocks: " + volume);
            }
        }

        return true;
    }
}
