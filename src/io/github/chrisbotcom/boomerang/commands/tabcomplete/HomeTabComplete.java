/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.commands.tabcomplete;

import io.github.chrisbotcom.boomerang.Boomerang;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

/**
 *
 * @author Chris
 */
public class HomeTabComplete implements TabCompleter {
    Boomerang plugin;

    public HomeTabComplete(Boomerang plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        }
        
        Player player = (Player) sender;

        List<String> list = new ArrayList<>();

        switch (args.length) {
            case 1:
                list.addAll(plugin.getSettings().getHomes(player));
                break;
        }

        return list;
    }
}
