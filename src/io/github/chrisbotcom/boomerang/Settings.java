/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.chrisbotcom.boomerang;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 *
 * @author chrisbot
 */
public final class Settings {

    private final Boomerang plugin;
    private final FileConfiguration config;

    private Location spawn = null;
    private int maxHomes = 0;
    private int tprequest_expire = 30;
    private int tprequest_cooldown = 20;
    private final Map<UUID, Map<String, Location>> homes;
    private List<Player> muteList;

    public int getMaxHomes() {
        return maxHomes;
    }

    public Settings(Boomerang plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.homes = new HashMap<>();
        this.muteList = new ArrayList<>();
        config.options().copyDefaults(true);
        plugin.saveConfig();
        loadSpawnConfig();
        loadMaxHomes();
        this.tprequest_expire = config.getInt("tprequest_expire");
        this.tprequest_cooldown = config.getInt("tprequest_cooldown");
    }

    public boolean isSpawnSet() {
        return (spawn != null);
    }

    public void setSpawn(Location location) {
        location.setX(Math.floor(location.getX()) + 0.5);
        location.setY(Math.floor(location.getY()) + 0.5);
        location.setZ(Math.floor(location.getZ()) + 0.5);
        location.setYaw((float) Math.floor(location.getYaw()));
        location.setPitch((float) Math.floor(location.getPitch()));
        config.set("spawn.world", location.getWorld().getName());
        config.set("spawn.x", location.getX());
        config.set("spawn.y", location.getY());
        config.set("spawn.z", location.getZ());
        config.set("spawn.yaw", location.getYaw());
        config.set("spawn.pitch", location.getPitch());
        plugin.saveConfig();
    }

    public Location getSpawn() {
        return spawn;
    }

    public void loadSpawnConfig() {
        if (config.contains("spawn")) {
            World world = Bukkit.getWorld(config.getString("spawn.world"));
            Vector vector = new Vector(config.getDouble("spawn.x"), config.getDouble("spawn.y"), config.getDouble("spawn.z"));
            double yaw = config.getDouble("spawn.yaw");
            double pitch = config.getDouble("spawn.pitch");
            Location location = vector.toLocation(world, (float) yaw, (float) pitch);
            spawn = location;
        }
    }

    private void loadMaxHomes() {
        File dataFolder = this.plugin.getDataFolder();
        File homesFolder = new File(dataFolder, "homes");
        if (!homesFolder.isDirectory()) {
            homesFolder.mkdir();
        }

        this.maxHomes = config.getInt("homes_max");
    }

    public void loadOnlinePlayersHomes() {
        this.homes.clear();

        for (Player player : Bukkit.getOnlinePlayers()) {
            loadPlayerHomes(player);
        }
    }

    public void loadPlayerHomes(Player player) {
        File dataFolder = this.plugin.getDataFolder();
        File homesFolder = new File(dataFolder, "homes");
        String playerUuidString = player.getUniqueId().toString();
        File playerHomesFile = new File(homesFolder, playerUuidString + ".yml");

        if ((playerHomesFile.exists()) && (playerHomesFile.length() > 0)) {
            Map<String, Location> map = new HashMap<>();
            YamlConfiguration homesConfig = new YamlConfiguration();
            try {
                homesConfig.load(playerHomesFile);
            } catch (IOException | InvalidConfigurationException ex) {
                Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            Set<String> playerHomes = homesConfig.getKeys(false);
            for (String playerHome : playerHomes) {
                Vector vector = new Vector(homesConfig.getDouble(playerHome + ".x"), homesConfig.getDouble(playerHome + ".y"), homesConfig.getDouble(playerHome + ".z"));
                double yaw = homesConfig.getDouble(playerHome + ".yaw");
                double pitch = homesConfig.getDouble(playerHome + ".pitch");
                Location location = vector.toLocation(Bukkit.getWorld(homesConfig.getString(playerHome + ".world")), (float) yaw, (float) pitch);
                map.put(playerHome, location);
            }
            this.homes.put(player.getUniqueId(), map);
        }
    }

    public Location getHome(Player player, String home) {
        Location location = null;
        if (this.homes.containsKey(player.getUniqueId())) {
            if (this.homes.get(player.getUniqueId()).containsKey(home)) {
                location = this.homes.get(player.getUniqueId()).get(home);
            }
        }
        return location;
    }

    public Set<String> getHomes(Player player) {
        if (this.homes.containsKey(player.getUniqueId())) {
            return this.homes.get(player.getUniqueId()).keySet();
        } else {
            return null;
        }
    }

    public void setHome(Player player, final String home) {
        final Location location = player.getLocation();
        location.setX(Math.floor(location.getX()) + 0.5);
        location.setY(Math.floor(location.getY()) + 0.5);
        location.setZ(Math.floor(location.getZ()) + 0.5);
        location.setYaw((float) Math.floor(location.getYaw()));
        location.setPitch((float) Math.floor(location.getPitch()));
        final String playerUuidString = player.getUniqueId().toString();

        Map<String, Location> map = new HashMap<>();
        map.put(home, location);
        if (!this.homes.containsKey(player.getUniqueId())) {
            this.homes.put(player.getUniqueId(), map);
        } else {
            this.homes.get(player.getUniqueId()).put(home, location);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                File dataFolder = plugin.getDataFolder();
                File homesFolder = new File(dataFolder, "homes");
                File playerHomesFile = new File(homesFolder, playerUuidString + ".yml");
                YamlConfiguration homesConfig = new YamlConfiguration();
                try {
                    homesConfig.load(playerHomesFile);
                } catch (IOException | InvalidConfigurationException ex) {
                    Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }
                config.set(home + ".world", location.getWorld().getName());
                config.set(home + ".x", location.getX());
                config.set(home + ".y", location.getY());
                config.set(home + ".z", location.getZ());
                config.set(home + ".yaw", location.getYaw());
                config.set(home + ".pitch", location.getPitch());
                try {
                    homesConfig.save(playerHomesFile);
                } catch (IOException ex) {
                    Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.runTaskLaterAsynchronously(plugin, 1);
    }

    public int getTpRequest_expire() {
        return tprequest_expire;
    }

    public int getTpRequest_cooldown() {
        return tprequest_cooldown;
    }

    public void removePlayerHomes(Player player) {
        homes.remove(player.getUniqueId());
    }

    public List<Player> getMuteList() {
        return muteList;
    }
}
