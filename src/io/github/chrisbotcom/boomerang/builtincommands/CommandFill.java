/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.builtincommands;

import io.github.chrisbotcom.boomerang.Boomerang;
import io.github.chrisbotcom.boomerang.builtincommands.tabcomplete.FillTabComplete;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author chrisbot
 */
public class CommandFill implements CommandExecutor {

    private final Boomerang plugin;

    public CommandFill(Boomerang plugin) {
        this.plugin = plugin;
        plugin.getCommand("fill").setTabCompleter(new FillTabComplete());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        String arguments = "";
        for (String arg : args) {
            arguments += " " + arg;
        }
        
        return plugin.getServer().dispatchCommand(sender, "minecraft:fill" + arguments);
    }
}
