package com.mizuledevelopment.zcaptcha;

import java.util.concurrent.TimeUnit;

public class zCaptchaConfig {

    public static final String CAPTCHA_REQUIRED = zCaptcha.getInstance().getConfig().getString("messages.captcha_required");
    public static final String CAPTCHA_COMPLETED = zCaptcha.getInstance().getConfig().getString("messages.captcha_completed");
    public static final String KICK_COMMAND = zCaptcha.getInstance().getConfig().getString("settings.command");
    public static final long TIME_TO_COMPLETE = TimeUnit.SECONDS.toMillis(zCaptcha.getInstance().getConfig().getInt("settings.time_to_complete"));
    public static final long RUNNABLE_DELAY = zCaptcha.getInstance().getConfig().getInt("settings.runnable_delay");

}
