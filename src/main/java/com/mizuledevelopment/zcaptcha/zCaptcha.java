package com.mizuledevelopment.zcaptcha;

import com.mizuledevelopment.zcaptcha.captcha.CaptchaHandler;
import com.mizuledevelopment.zcaptcha.requirement.RequirementHandler;
import com.mizuledevelopment.zcaptcha.storage.IStorage;
import com.mizuledevelopment.zcaptcha.storage.impl.FlatFileStorageImpl;
import com.mizuledevelopment.zcaptcha.storage.impl.MongoStorageImpl;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class zCaptcha extends JavaPlugin {

    @Getter private static zCaptcha instance;

    private IStorage storage;

    private RequirementHandler requirementHandler;

    private CaptchaHandler captchaHandler;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        storage = getConfig().getString("settings.database").equalsIgnoreCase("MONGO") ? new MongoStorageImpl(this) : new FlatFileStorageImpl(this);
        requirementHandler = new RequirementHandler();

        captchaHandler = new CaptchaHandler();
    }

    @Override
    public void onDisable() {
        storage.saveAll();
    }
}
