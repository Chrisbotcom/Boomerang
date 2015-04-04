/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.builtincommands;

import io.github.chrisbotcom.boomerang.Boomerang;
import io.github.chrisbotcom.boomerang.builtincommands.tabcomplete.FillTabComplete;
import io.github.chrisbotcom.boomerang.builtincommands.tabcomplete.SetblockTabComplete;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author chrisbot
 */
public class CommandSetblock implements CommandExecutor {

    private final Boomerang plugin;

    public CommandSetblock(Boomerang plugin) {
        this.plugin = plugin;
        plugin.getCommand("setblock").setTabCompleter(new SetblockTabComplete());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        String arguments = "";
        for (String arg : args) {
            arguments += " " + arg;
        }
        
        return plugin.getServer().dispatchCommand(sender, "minecraft:setblock" + arguments);
    }
}
