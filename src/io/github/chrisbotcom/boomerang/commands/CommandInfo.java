/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.commands;

import io.github.chrisbotcom.boomerang.Boomerang;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author chrisbot
 */
public class CommandInfo implements CommandExecutor {

    private final Boomerang plugin;

    public CommandInfo(Boomerang plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Info command cannot be run from console.");
            return true;
        }
        
        if (args.length > 0) {
            sender.sendMessage(ChatColor.RED + "/info accepts no arguments.");
            return false;
        }

        Player player = (Player) sender;
        
        Block block = player.getTargetBlock((Set<Material>) null, 10);
        player.sendMessage("Type(Data): " + block.getState().getData().toString());
        
        return true;
    }
}
