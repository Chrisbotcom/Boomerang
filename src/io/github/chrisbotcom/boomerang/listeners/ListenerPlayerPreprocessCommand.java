/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.listeners;

import java.io.Console;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpMap;
import org.bukkit.help.HelpTopic;

/**
 *
 * @author chrisbot
 */
public class ListenerPlayerPreprocessCommand implements Listener {

    @EventHandler
    public void onPlayerPreprocessCommand(PlayerCommandPreprocessEvent e) {

//        Bukkit.getLogger().info(">>" + e.getMessage() + "<<");
//
//        for (HelpTopic cmdLabel : e.getPlayer().getServer().getHelpMap().getHelpTopics()) {
//            Bukkit.getLogger().info(">>" + cmdLabel.getName() + " " + cmdLabel.getShortText() + "<<");
//        }
//        
//        Bukkit.getLogger().info(e.getPlayer().getUniqueId().toString());
//        HelpTopic ht = e.getPlayer().getServer().getHelpMap().getHelpTopic("Bukkit");
//        Bukkit.getLogger().info("**" + ht.getFullText(Bukkit.getConsoleSender()) + "**");
        
    }
}
