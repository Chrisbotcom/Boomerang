/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.commands;

import io.github.chrisbotcom.boomerang.Boomerang;
import io.github.chrisbotcom.boomerang.types.SelectionType;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;

/**
 *
 * @author chrisbot
 */
public class CommandRegen implements CommandExecutor {

    private final Boomerang plugin;

    public CommandRegen(Boomerang plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender == plugin.getServer().getConsoleSender()) {
            sender.sendMessage(ChatColor.RED + "ERROR: regen cannot be called from console.");
            return true;
        }
        if (args.length != 0) {
            sender.sendMessage(ChatColor.RED + "/regen accepts no argument.");
            return false;
        }
        Player player = (Player) sender;
        World world = player.getWorld();

        SelectionType selection = plugin.getSettings().getPosSelection(player);
        if (!selection.isValid()) {
            sender.sendMessage(ChatColor.RED + "Pos selection is not valid.");
        } else {
            int x1 = Math.min(selection.getPos1().getBlockX(), selection.getPos2().getBlockX()) >> 4;
            int x2 = Math.max(selection.getPos1().getBlockX(), selection.getPos2().getBlockX()) >> 4;
            int z1 = Math.min(selection.getPos1().getBlockZ(), selection.getPos2().getBlockZ()) >> 4;
            int z2 = Math.max(selection.getPos1().getBlockZ(), selection.getPos2().getBlockZ()) >> 4;
            
            for (int i = x1; i <= x2; i++) {
                for (int j = z1; j <= z2; j++) {
                    if (world.unloadChunk(i, j)) {
                        if (world.regenerateChunk(i, j)) {
                            world.loadChunk(i, j);
                            player.sendMessage(ChatColor.GREEN + "Regenerated chunk.");
                        } else {
                            player.sendMessage(ChatColor.RED + "Could not regenerate chunk.");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Could not unload chunk.");
                    }
                    
                }
            }
            sender.sendMessage(ChatColor.YELLOW + "Chunks regnerated.");
        }

        return true;
    }
}
