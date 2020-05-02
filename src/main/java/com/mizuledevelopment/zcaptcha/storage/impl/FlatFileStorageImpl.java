package com.mizuledevelopment.zcaptcha.storage.impl;

import com.mizuledevelopment.zcaptcha.storage.IStorage;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class FlatFileStorageImpl implements IStorage {

    private Set<UUID> alreadyAuthenticated = new HashSet<>();

    private File file;

    private YamlConfiguration config;

    public FlatFileStorageImpl(Plugin plugin) {
        file = new File(plugin.getDataFolder().getAbsolutePath(), "storage.yml");

        if (!file.exists()) {
            plugin.saveResource("storage.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(file);

        if (config.getStringList("authenticated") != null) {
            for (String uuidStr : config.getStringList("authenticated")) {
                alreadyAuthenticated.add(UUID.fromString(uuidStr));
            }
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                saveAll();
            }
        }.runTaskTimerAsynchronously(plugin, 0L, 20*60*15);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasUserAuthenticated(UUID uuid) {
        return alreadyAuthenticated.contains(uuid);
    }

    @Override
    public void addUserAuthenticated(UUID uuid) {
        alreadyAuthenticated.add(uuid);
    }

    @Override
    public void saveAll() {
        config.set("authenticated", alreadyAuthenticated.stream().map(UUID::toString).collect(Collectors.toList()));
        save();
    }
}
