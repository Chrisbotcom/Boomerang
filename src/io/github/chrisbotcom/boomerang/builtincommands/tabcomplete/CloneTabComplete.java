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
public class CloneTabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        }
        
        Player player = (Player) sender;

        List<String> list = new ArrayList<>();

        switch (args.length) {
            case 1:
            case 4:
            case 7:
                list.add(String.valueOf(player.getTargetBlock((Set<Material>) null, 10).getLocation().getBlockX()));
                break;
            case 2:
            case 5:
            case 8:
                list.add(String.valueOf(player.getTargetBlock((Set<Material>) null, 10).getLocation().getBlockY()));
                break;
            case 3:
            case 6:
            case 9:
                list.add(String.valueOf(player.getTargetBlock((Set<Material>) null, 10).getLocation().getBlockZ()));
                break;
            case 10:
                list.add("filtered");
                list.add("masked");
                list.add("replace");
                break;
            case 11:
                list.add("force");
                list.add("move");
                list.add("normal");
                break;
            case 12:
                for (Material material : Material.values()) {
                    list.add(material.name().toLowerCase());
                }
                break;
        }

        return list;
    }
}
