/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang;

import io.github.chrisbotcom.boomerang.builtincommands.CommandFill;
import io.github.chrisbotcom.boomerang.builtincommands.CommandClone;
import io.github.chrisbotcom.boomerang.builtincommands.CommandExecute;
import io.github.chrisbotcom.boomerang.builtincommands.CommandParticle;
import io.github.chrisbotcom.boomerang.builtincommands.CommandPlaysound;
import io.github.chrisbotcom.boomerang.builtincommands.CommandSetblock;
import io.github.chrisbotcom.boomerang.commands.*;
import io.github.chrisbotcom.boomerang.commands.tprequest.Requests;
import io.github.chrisbotcom.boomerang.listeners.*;
import io.github.chrisbotcom.boomerang.votelistener.VoteListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author chrisbot
 */
public class Boomerang extends JavaPlugin implements Listener {
    private Settings settings;
    private Requests tpRequests;

    @Override
    public void onEnable() {
        settings = new Settings(this);
        settings.loadOnlinePlayersHomes();
        VoteListener voteListener = new VoteListener(this, settings.getVoteListenerPort());
        voteListener.start();

        try {
            File dataFolder = this.getDataFolder();
            File f = new File(dataFolder, "commands_join.txt");
            if (!f.exists()) { f.createNewFile(); }
            f = new File(dataFolder, "commands_new_join.txt");
            if (!f.exists()) { f.createNewFile(); }
        } catch (IOException ex) {
            Logger.getLogger(Boomerang.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.tpRequests = new Requests(this);

        getServer().getPluginManager().registerEvents(new ListenerPlayerChat(this), this);
        getServer().getPluginManager().registerEvents(new ListenerPlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new ListenerPlayerQuit(this), this);
        getServer().getPluginManager().registerEvents(new ListenerPlayerPreprocessCommand(), this);
        getServer().getPluginManager().registerEvents(new ListenerPlayerRespawn(this), this);
        
        
        //getServer().getPluginManager().registerEvents(new ListenerCreatureSpawn(this), this);

        getCommand("setworldspawn").setExecutor(new CommandSetWorldSpawn(this));
        getCommand("spawn").setExecutor(new CommandSpawn(this));
        getCommand("reload").setExecutor(new CommandReload(this));
        getCommand("sethome").setExecutor(new CommandSetHome(this));
        getCommand("delhome").setExecutor(new CommandDelHome(this));
        getCommand("home").setExecutor(new CommandHome(this));
        getCommand("tprequest").setExecutor(new CommandTPRequest(this));
        getCommand("tphere").setExecutor(new CommandTPHere(this));
        getCommand("tpaccept").setExecutor(new CommandTPAccept(this));
        getCommand("tpdeny").setExecutor(new CommandTPDeny(this));
        getCommand("tpcancel").setExecutor(new CommandTPCancel(this));
        getCommand("tplist").setExecutor(new CommandTPList(this));
        getCommand("fly").setExecutor(new CommandFly(this));
        getCommand("nightvision").setExecutor(new CommandNightvision(this));
        getCommand("mute").setExecutor(new CommandMute(this));
        getCommand("unmute").setExecutor(new CommandUnmute(this));
        getCommand("info").setExecutor(new CommandInfo(this));
//        getCommand("pos").setExecutor(new CommandPos(this));
//        getCommand("pos1").setExecutor(new CommandPos1(this));
//        getCommand("pos2").setExecutor(new CommandPos2(this));
//        getCommand("regen").setExecutor(new CommandRegen(this));
        
        // Built-in Minecraft commands.
        getCommand("fill").setExecutor(new CommandFill(this));
        getCommand("clone").setExecutor(new CommandClone(this));
        getCommand("execute").setExecutor(new CommandExecute(this));
        getCommand("particle").setExecutor(new CommandParticle(this));
        getCommand("playsound").setExecutor(new CommandPlaysound(this));
        getCommand("setblock").setExecutor(new CommandSetblock(this));
    }

    @Override
    public void onDisable() {
        //this.protocolManager.removePacketListener(listenerPacket);
        Bukkit.getScheduler().cancelTasks(this);
    }
    
    public Settings getSettings() {
        return this.settings;
    }

    public Requests getTpRequests() {
        return tpRequests;
    }
}
