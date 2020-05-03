package com.mizuledevelopment.zcaptcha.captcha;

import com.mizuledevelopment.zcaptcha.zCaptcha;
import com.mizuledevelopment.zcaptcha.zCaptchaConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class CaptchaCheckRunnable extends BukkitRunnable {

    public CaptchaCheckRunnable() {
        runTaskTimerAsynchronously(zCaptcha.getInstance(), 0L, zCaptchaConfig.RUNNABLE_DELAY);
    }

    @Override
    public void run() {
        for (UUID uuid : zCaptcha.getInstance().getCaptchaHandler().getRequiresAuth().keySet()) {
            Player player = Bukkit.getPlayer(uuid);

            if (player != null) {
                if (zCaptcha.getInstance().getCaptchaHandler().checkIfAuthenticated(player)) {
                    zCaptcha.getInstance().getCaptchaHandler().completeCaptcha(player);
                }
            }
        }
    }

}
