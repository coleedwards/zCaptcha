package com.mizuledevelopment.zcaptcha.storage;

import java.util.UUID;

public interface IStorage {

    boolean hasUserAuthenticated(UUID uuid);
    void addUserAuthenticated(UUID uuid);

    void saveAll();

}
