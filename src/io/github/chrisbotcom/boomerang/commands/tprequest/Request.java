/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.commands.tprequest;

import java.util.HashMap;
import org.bukkit.entity.Player;

/**
 *
 * @author chrisbot
 */
public class Request extends HashMap {
    private final Player requester;
    private final Player recipient;
    private final RequestType type;
    
    public Request(Player requester, Player recipient, RequestType type) {
        this.requester = requester;
        this.recipient = recipient;
        this.type = type;
    }

    public Player getRequester() {
        return requester;
    }

    public Player getRecipient() {
        return recipient;
    }

    public RequestType getType() {
        return type;
    }
}
