package com.mizuledevelopment.zcaptcha.captcha.runnable;

import com.mizuledevelopment.zcaptcha.CaptchaPlugin;
import com.mizuledevelopment.zcaptcha.CaptchaPluginConstants;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

public class CaptchaCooldownRunnable extends BukkitRunnable {

    public CaptchaCooldownRunnable() {
        runTaskTimerAsynchronously(CaptchaPlugin.getInstance(), 0L, 20L);
    }

    @Override
    public void run() {
        for (Map.Entry<UUID, Long> entry : CaptchaPlugin.getInstance().getCaptchaHandler().getCooldown().entrySet()) {
            if (System.currentTimeMillis() >= entry.getValue()) {
                Player player = Bukkit.getPlayer(entry.getKey());

                if (player != null) {
                    Bukkit.getServer().getScheduler().runTask(CaptchaPlugin.getInstance(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), CaptchaPluginConstants.KICK_COMMAND.replace("%player%", player.getName())));
                }
            }
        }
    }
}
