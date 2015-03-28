/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang.votelistener;

import io.github.chrisbotcom.boomerang.Boomerang;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.command.CommandException;

/**
 *
 * @author chrisbot
 */
public class CommandSender implements Runnable {

    Boomerang plugin;
    private final String player;
    private final String service;
    public final File voteFolder;

    CommandSender(Boomerang plugin, File voteFolder, String player, String service) {
        this.plugin = plugin;
        this.player = player;
        this.service = service;
        this.voteFolder = voteFolder;
    }

    @Override
    public void run() {
        try {
            File f = new File(voteFolder, "command.txt");
            File f_log = new File(voteFolder, "log.txt");
            FileWriter log = new FileWriter(f_log);
            Scanner s = new Scanner(f);
            String line;
            while (s.hasNext()) {
                line = s.nextLine()
                        .replace("@p", player)
                        .replace("@s", service);
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), line);
                log.write(new Date().toString() + ": " + line + "\n");
            }
            log.close();
        } catch (IOException | CommandException ex) {
            Logger.getLogger(CommandSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
