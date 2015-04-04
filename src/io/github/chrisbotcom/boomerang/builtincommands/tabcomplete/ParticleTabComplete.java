/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.builtincommands.tabcomplete;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

/**
 *
 * @author Chris
 */
public class ParticleTabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        }
        
        Player player = (Player) sender;

        List<String> list = new ArrayList<>();

        switch (args.length) {
            case 2:
                list.add(String.valueOf(player.getTargetBlock((Set<Material>) null, 10).getLocation().getBlockX()));
                break;
            case 3:
                list.add(String.valueOf(player.getTargetBlock((Set<Material>) null, 10).getLocation().getBlockY()));
                break;
            case 4:
                list.add(String.valueOf(player.getTargetBlock((Set<Material>) null, 10).getLocation().getBlockZ()));
                break;
        }

        return list;
    }
}
