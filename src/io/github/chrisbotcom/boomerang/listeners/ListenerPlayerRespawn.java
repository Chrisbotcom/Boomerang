/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.listeners;

import io.github.chrisbotcom.boomerang.Boomerang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 *
 * @author chrisbot
 */
public class ListenerPlayerRespawn implements Listener {

    private final Boomerang plugin;

    public ListenerPlayerRespawn(Boomerang plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();

        if (!e.isBedSpawn()) {
            if (plugin.getSettings().isSpawnSet()) {
                e.setRespawnLocation(plugin.getSettings().getSpawn());
            }
        }
    }
}
