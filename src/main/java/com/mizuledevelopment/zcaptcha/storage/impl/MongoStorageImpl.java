package com.mizuledevelopment.zcaptcha.storage.impl;

import com.mizuledevelopment.zcaptcha.storage.IStorage;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class MongoStorageImpl implements IStorage {

    private Set<UUID> alreadyAuthenticated = new HashSet<>();

    private MongoCollection<Document> mongoCollection;

    public MongoStorageImpl(Plugin plugin) {
        MongoClient mongoClient;

        ServerAddress serverAddress = new ServerAddress(plugin.getConfig().getString("settings.mongo.host"), plugin.getConfig().getInt("settings.mongo.port"));

        if (plugin.getConfig().getBoolean("settings.mongo.auth.enabled")) {
            mongoClient = new MongoClient(serverAddress, Collections.singletonList(MongoCredential.createCredential(
                    plugin.getConfig().getString("settings.mongo.auth.username"),
                    plugin.getConfig().getString("settings.mongo.auth.database"),
                    plugin.getConfig().getString("settings.mongo.auth.password").toCharArray()
            )));
        } else {
            mongoClient = new MongoClient(serverAddress);
        }

        mongoCollection = mongoClient.getDatabase(plugin.getConfig().getString("settings.mongo.database")).getCollection("storage");

        Document document = mongoCollection.find().first();

        if (document != null) {
            for (Object value : document.values()) {
                if (value instanceof String) {
                    alreadyAuthenticated.add(UUID.fromString((String) value));
                }
            }
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                saveAll();
            }
        }.runTaskTimerAsynchronously(plugin, 0L, 20*60*15);
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
        mongoCollection.drop();

        Document newDocument = new Document();

        int i = -1;
        for (UUID uuid : alreadyAuthenticated) {
            i++;
            newDocument.put(Integer.toString(i), uuid.toString());
        }

        mongoCollection.insertOne(newDocument);
    }
}
