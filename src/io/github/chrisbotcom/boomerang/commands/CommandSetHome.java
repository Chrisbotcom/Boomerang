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
public class CommandSetHome implements CommandExecutor {

    private final Boomerang plugin;

    public CommandSetHome(Boomerang plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "/sethome requires home name. You may set up to " + plugin.getSettings().getMaxHomes() + " homes.");
            return false;
        }

        Player player = (Player) sender;

        if (!player.isOp() && (plugin.getSettings().getHomes(player).size() >= plugin.getSettings().getMaxHomes())) {
            player.sendMessage(ChatColor.YELLOW + "Cannot set home. The maximum of " + plugin.getSettings().getMaxHomes() + " has already been set.");
        } else {
            String home = args[0].toLowerCase();
            plugin.getSettings().setHome(player, home);
            player.sendMessage(ChatColor.YELLOW + "Home '" + home + "' set.");
        }
        return true;
    }
}
