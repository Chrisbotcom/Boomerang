/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.commands.tprequest;

import io.github.chrisbotcom.boomerang.Boomerang;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

/**
 *
 * @author chrisbot
 */
public class Requests {

    private final Map<Long, Request> map;

    public Requests(Boomerang plugin) {
        this.map = new HashMap<>();
        
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.runTaskTimerAsynchronously(plugin, new RequestTask(plugin), 20L, 20L);
    }

    /**
     * Adds a TP request to the list. If <i>requester</i> has a request pending,
     * it is removed from the list and this request is added and true is
     * returned. Otherwise, the request is added and false is returned. The
     * request list is pruned of expired requests.
     *
     * @param requester the player issuing the request.
     * @param recipient the player receiving the request.
     * @param type the type of request, /tprequest or /tphere.
     * @return true if this request replaces an existing request.
     * @see RequestType
     */
    public boolean addRequest(Player requester, Player recipient, RequestType type) {
        boolean returnValue = false;

        synchronized (map) {
            for (Iterator<Map.Entry<Long, Request>> it = map.entrySet().iterator(); it.hasNext();) {
                Map.Entry<Long, Request> entry = it.next();
                if (entry.getValue().getRequester().equals(requester)) {
                    returnValue = true;
                    it.remove();
                    break;
                }
            }
            map.put(new Date().getTime(), new Request(requester, recipient, type));
        }

        return returnValue;
    }

    /**
     * Removes a request from this <i>requester</i>.
     * 
     * @param requester the player making the request.
     */
    public void removeRequestFrom(Player requester) {
        synchronized (map) {
            for (Iterator<Map.Entry<Long, Request>> it = map.entrySet().iterator(); it.hasNext();) {
                Map.Entry<Long, Request> entry = it.next();
                if (entry.getValue().getRequester().equals(requester)) {
                    it.remove();
                    break;
                }
            }
        }
    }

    /**
     * Removed a request for <i>recipient</i> from <i>requester</i>.
     * 
     * @param recipient the players receiving the request.
     * @param requester the player making the request.
     */
    public void removeRequestForFrom(Player recipient, Player requester) {
        synchronized (map) {
            for (Iterator<Map.Entry<Long, Request>> it = map.entrySet().iterator(); it.hasNext();) {
                Map.Entry<Long, Request> entry = it.next();
                if (entry.getValue().getRecipient().equals(recipient) && entry.getValue().getRequester().equals(requester)) {
                    it.remove();
                    break;
                }
            }
        }
    }

    public RequestType removeRequestTypeForFrom(Player recipient, Player requester) {
        RequestType returnValue = null;
        
        synchronized (map) {
            for (Map.Entry<Long, Request> entry : map.entrySet()) {
                if (entry.getValue().getRecipient().equals(recipient) && entry.getValue().getRequester().equals(requester)) {
                    returnValue = entry.getValue().getType();
                    break;
                }
            }
        }
        
        return returnValue;
    }

    public List<Player> getRequestsPendingFor(Player recipient) {
        List<Player> list = new ArrayList<>();
        
        synchronized (map) {
            for (Map.Entry<Long, Request> entry : map.entrySet()) {
                if (entry.getValue().getRecipient().equals(recipient)) {
                    list.add(entry.getValue().getRequester());
                }
            }
        }
        
        return list;
    }

    public boolean hasRequestPending(Player recipient) {
        boolean returnValue = false;
        
        synchronized (map) {
            for (Map.Entry<Long, Request> entry : map.entrySet()) {
                if (entry.getValue().getRecipient().equals(recipient)) {
                    returnValue = true;
                    break;
                }
            }
        }
        
        return returnValue;
    }
    
    public boolean hasRequestPendingFrom(Player recipient, Player requester) {
        boolean returnValue = false;
        
        synchronized (map) {
            for (Map.Entry<Long, Request> entry : map.entrySet()) {
                if (entry.getValue().getRecipient().equals(recipient) && entry.getValue().getRequester().equals(requester)) {
                    returnValue = true;
                    break;
                }
            }
        }
        
        return returnValue;
    }

    public Map<Long, Request> getMap() {
        return map;
    }
}
