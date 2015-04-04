/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.builtincommands;

import io.github.chrisbotcom.boomerang.Boomerang;
import io.github.chrisbotcom.boomerang.builtincommands.tabcomplete.ParticleTabComplete;
import io.github.chrisbotcom.boomerang.builtincommands.tabcomplete.PlaysoundTabComplete;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author chrisbot
 */
public class CommandPlaysound implements CommandExecutor {

    private final Boomerang plugin;

    public CommandPlaysound(Boomerang plugin) {
        this.plugin = plugin;
        plugin.getCommand("playsound").setTabCompleter(new PlaysoundTabComplete());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        String arguments = "";
        for (String arg : args) {
            arguments += " " + arg;
        }
        
        return plugin.getServer().dispatchCommand(sender, "minecraft:playsound" + arguments);
    }
}
