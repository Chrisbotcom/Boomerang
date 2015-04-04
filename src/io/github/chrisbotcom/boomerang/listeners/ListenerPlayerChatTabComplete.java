/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.listeners;

import io.github.chrisbotcom.boomerang.Boomerang;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

/**
 *
 * @author chrisbot
 */
public class ListenerPlayerChatTabComplete implements Listener {

    private final Boomerang plugin;

    public ListenerPlayerChatTabComplete(Boomerang plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChatTabComplete(PlayerChatTabCompleteEvent e) {
    //public void onPlayerChatTabCompleteEvent(Player sender, String message, Collection<String> completions) {
        //String[] tokens = e.getChatMessage().split(" ");
        //Player player = e.getPlayer();
        
        plugin.getLogger().info("test");
        
//        if (tokens[0].equalsIgnoreCase("/pos1")) {
//            Set<Material> set = new HashSet<>();
//            Block block = player.getTargetBlock(set, 100);
//            e.getTabCompletions().add(block.getLocation().toVector().toString());
//        }
        
//        List<String> list = new ArrayList<>();
//        list.add("test");
//        return list;
    }
}
