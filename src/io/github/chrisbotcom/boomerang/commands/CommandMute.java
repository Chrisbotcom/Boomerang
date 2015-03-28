/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.commands;

import io.github.chrisbotcom.boomerang.Boomerang;
import java.util.ArrayList;
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
public class CommandMute implements CommandExecutor {

    private final Boomerang plugin;

    public CommandMute(Boomerang plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length > 1) {
            sender.sendMessage(ChatColor.RED + "/mute accepts one optional argument.");
            return false;
        }
        
        if (args.length == 0) {
            List<String> list = new ArrayList<>();
            for (Player player : plugin.getSettings().getMuteList()) {
                list.add(player.getName());
            }
            sender.sendMessage(String.format(ChatColor.YELLOW + "Muted player(s): %s", list.toString().replaceAll("[\\[\\]]", "")));
        } else {
            Player player = plugin.getServer().getPlayer(args[0]);
            
            if (player == null) {
                sender.sendMessage(ChatColor.RED + "Player " + args[0] + " not found.");
            } else {
                plugin.getSettings().getMuteList().add(player);
                sender.sendMessage(ChatColor.YELLOW + "Player " + args[0] + " muted.");
            }
        }

        return true;
    }
}
