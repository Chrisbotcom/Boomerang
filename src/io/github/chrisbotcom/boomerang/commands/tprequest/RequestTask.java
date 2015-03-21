/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.commands.tprequest;

import io.github.chrisbotcom.boomerang.Boomerang;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author chrisbot
 */
public class RequestTask implements Runnable {

    private final Boomerang plugin;
    private final long expire_milliseconds;

    public RequestTask(Boomerang plugin) {
        this.plugin = plugin;
        expire_milliseconds = plugin.getSettings().getTpRequest_expire() * 1000;
    }

    @Override
    public void run() {
        long expired = new Date().getTime() - expire_milliseconds;
        Map<Long,Request> map = plugin.getTpRequests().getMap();
        synchronized (map) {
            for (Iterator<Map.Entry<Long, Request>> it = map.entrySet().iterator(); it.hasNext();) {
                Map.Entry<Long, Request> entry = it.next();
                if (entry.getKey() < expired) {
                    Player requester = entry.getValue().getRequester();
                    Player recipient = entry.getValue().getRecipient();
                    it.remove();
                    entry.getValue().getRequester().sendMessage(ChatColor.YELLOW + "Your teleport request to " + recipient.getName() + " has expired.");
                    entry.getValue().getRecipient().sendMessage(ChatColor.YELLOW + requester.getName() + "'s teleport request has expired.");
                }
            }
        }
    }
}
