package com.mizuledevelopment.zcaptcha;

import java.util.concurrent.TimeUnit;

public class CaptchaPluginConstants {

    public static final String CAPTCHA_REQUIRED = CaptchaPlugin.getInstance().getConfig().getString("messages.captcha_required");
    public static final String CAPTCHA_COMPLETED = CaptchaPlugin.getInstance().getConfig().getString("messages.captcha_completed");
    public static final String KICK_COMMAND = CaptchaPlugin.getInstance().getConfig().getString("settings.command");
    public static final long TIME_TO_COMPLETE = TimeUnit.SECONDS.toMillis(CaptchaPlugin.getInstance().getConfig().getInt("settings.time_to_complete"));
    public static final long RUNNABLE_DELAY = CaptchaPlugin.getInstance().getConfig().getInt("settings.runnable_delay");

}
