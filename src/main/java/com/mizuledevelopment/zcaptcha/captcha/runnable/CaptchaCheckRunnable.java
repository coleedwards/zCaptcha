package com.mizuledevelopment.zcaptcha.captcha.runnable;

import com.mizuledevelopment.zcaptcha.CaptchaPlugin;
import com.mizuledevelopment.zcaptcha.CaptchaPluginConstants;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class CaptchaCheckRunnable extends BukkitRunnable {

    public CaptchaCheckRunnable() {
        runTaskTimerAsynchronously(CaptchaPlugin.getInstance(), 0L, CaptchaPluginConstants.RUNNABLE_DELAY);
    }

    @Override
    public void run() {
        for (UUID uuid : CaptchaPlugin.getInstance().getCaptchaHandler().getRequiresAuth().keySet()) {
            Player player = Bukkit.getPlayer(uuid);

            if (player != null) {
                if (CaptchaPlugin.getInstance().getCaptchaHandler().checkIfAuthenticated(player)) {
                    CaptchaPlugin.getInstance().getCaptchaHandler().completeCaptcha(player);
                }
            }
        }
    }

}
