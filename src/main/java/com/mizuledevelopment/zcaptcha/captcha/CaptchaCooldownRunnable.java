package com.mizuledevelopment.zcaptcha.captcha;

import com.mizuledevelopment.zcaptcha.zCaptcha;
import com.mizuledevelopment.zcaptcha.zCaptchaConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

public class CaptchaCooldownRunnable extends BukkitRunnable {

    public CaptchaCooldownRunnable() {
        runTaskTimerAsynchronously(zCaptcha.getInstance(), 0L, 20L);
    }

    @Override
    public void run() {
        for (Map.Entry<UUID, Long> entry : zCaptcha.getInstance().getCaptchaHandler().getCooldown().entrySet()) {
            if (System.currentTimeMillis() >= entry.getValue()) {
                Player player = Bukkit.getPlayer(entry.getKey());

                if (player != null) {
                    Bukkit.getServer().getScheduler().runTask(zCaptcha.getInstance(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), zCaptchaConfig.KICK_COMMAND.replace("%player%", player.getName())));
                }
            }
        }
    }
}
