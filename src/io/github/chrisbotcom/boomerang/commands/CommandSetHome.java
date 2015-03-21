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

//                Location location = player.getLocation();
//                location.setX(Math.floor(location.getX()));
//                location.setY(Math.floor(location.getY()));
//                location.setZ(Math.floor(location.getZ()));
//                location.setYaw((float) Math.floor(location.getYaw()));
//                location.setPitch((float) Math.floor(location.getPitch()));
//                location = location.add(0.5, 0.5, 0.5);
//                homesConfig.set(home + ".world", location.getWorld().getName());
//                homesConfig.set(home + ".x", location.getX());
//                homesConfig.set(home + ".y", location.getY());
//                homesConfig.set(home + ".z", location.getZ());
//                homesConfig.set(home + ".yaw", location.getYaw());
//                homesConfig.set(home + ".pitch", location.getPitch());
//                homesConfig.save(playerHomesFile);
        return true;
    }
}
