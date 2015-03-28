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
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author chrisbot
 */
public class VoteListener extends Thread {

    private final Boomerang plugin;
    private final int listenerPort;
    private final File voteFolder;

    public VoteListener(Boomerang plugin, int listenerPort) {
        this.plugin = plugin;
        this.listenerPort = listenerPort;
        File dataFolder = this.plugin.getDataFolder();
        voteFolder = new File(dataFolder, "vote");
        if (!voteFolder.isDirectory()) {
            voteFolder.mkdir();
        }
        File commandFile = new File(voteFolder, "command.txt");
        if (!commandFile.exists()) {
            try {
                commandFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(VoteListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void run() {
        // Set up Logging.
        System.out.println(new Date().toString() + " Starting VoteListener...");

        // Load private key. 
        Crypto crypto = new Crypto(plugin, voteFolder);

        plugin.getLogger().info("======= Begin Public Key =======");
        plugin.getLogger().info(crypto.getPublicKeyBase64String());
        plugin.getLogger().info("======== End Public Key ========\n");

        // Connect to socket
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(listenerPort);
        } catch (IOException ex) {
            plugin.getLogger().severe(ex.toString());
        }

        if (serverSocket == null) {
            plugin.getLogger().severe("Could not bind VoteListener socket!");
        } else {
            while (!serverSocket.isBound()) {

            }
            plugin.getLogger().log(Level.INFO, "VoteListener socket bound to port {0}.", serverSocket.getLocalPort());

            // Listen for vote.
            byte[] b = new byte[256];
            while (true) {
                try (Socket server = serverSocket.accept()) {
                    server.getOutputStream().write("VOTIFIER 1.0".getBytes());
                    server.getOutputStream().flush();

                    server.setSoTimeout(250);
                    plugin.getLogger().log(Level.INFO, "Connected to {0}.", server.getRemoteSocketAddress());

                    try {
                        int i = server.getInputStream().read(b);
                        server.close();
                        plugin.getLogger().log(Level.INFO, "VoteListener received {0} bytes.", i);
                    } catch (SocketTimeoutException ex) {
                        plugin.getLogger().severe("VoteListener: SocketTimeoutException!");
                    } catch (IOException ex) {
                        plugin.getLogger().severe(ex.toString());
                    }

                    byte[] decryptedText = crypto.decrypt(b);

                    String[] votifierBlock = new String(decryptedText).split("\n");
                    if (votifierBlock[0].matches("VOTE")) {
                        String service = votifierBlock[1];
                        String player = votifierBlock[2];
                        String address = votifierBlock[3];
                        String timeStamp = votifierBlock[4];

                        File dataFolder = this.plugin.getDataFolder();
                        File f_log = new File(dataFolder, "log.txt");
                        FileWriter log = new FileWriter(f_log);
                        log.write(" Received Vote: service: " + service);
                        log.write(", player: " + player);
                        log.write(", address: " + address);
                        log.write(", timeStamp: " + timeStamp + "\n");
                        log.close();
                        CommandSender commandSender = new CommandSender(plugin, voteFolder, player, service);
                        commandSender.run();
                    } else {
                        Logger.getLogger(VoteListener.class.getName()).log(Level.SEVERE, null, "ERROR: Received erroneous Votifier Block:\n" + new String(decryptedText));
                    }

                    server.close();
                    //hasVote = true;
                } catch (NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | NoSuchPaddingException | BadPaddingException ex) {
                    Logger.getLogger(VoteListener.class.getName()).log(Level.SEVERE, null, "ERROR: Received erroneous Votifier Block.");
                } catch (IOException ex) {
                    Logger.getLogger(VoteListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
