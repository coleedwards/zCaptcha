package com.mizuledevelopment.zcaptcha.captcha;

import com.mizuledevelopment.zcaptcha.util.ColorUtil;
import com.mizuledevelopment.zcaptcha.zCaptcha;
import com.mizuledevelopment.zcaptcha.zCaptchaConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;

public class CaptchaListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (zCaptcha.getInstance().getRequirementHandler().requiresCaptcha(event.getPlayer())) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    String key = zCaptcha.getInstance().getCaptchaHandler().generateAccessKey(player);

                    if (key != null) {
                        player.sendMessage(ColorUtil.format(zCaptchaConfig.CAPTCHA_REQUIRED.replace("%key%", key)));
                    }
                }
            }.runTaskLater(zCaptcha.getInstance(), 1);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockY() == event.getTo().getBlockY() && event.getFrom().getBlockZ() == event.getTo().getBlockZ()) {
            return;
        }

        Player player = event.getPlayer();

        if (zCaptcha.getInstance().getCaptchaHandler().getCooldown().containsKey(player.getUniqueId()) && zCaptcha.getInstance().getCaptchaHandler().getRequiresAuth().containsKey(player.getUniqueId())) {
            event.setTo(event.getFrom());

            String key = zCaptcha.getInstance().getCaptchaHandler().generateAccessKey(player);

            player.sendMessage(ColorUtil.format(zCaptchaConfig.CAPTCHA_REQUIRED.replace("%key%", key)));
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (zCaptcha.getInstance().getCaptchaHandler().getCooldown().containsKey(player.getUniqueId()) && zCaptcha.getInstance().getCaptchaHandler().getRequiresAuth().containsKey(player.getUniqueId())) {
            if (!event.getMessage().equalsIgnoreCase("/checkauth")) {
                String key = zCaptcha.getInstance().getCaptchaHandler().generateAccessKey(player);

                player.sendMessage(ColorUtil.format(zCaptchaConfig.CAPTCHA_REQUIRED.replace("%key%", key)));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (zCaptcha.getInstance().getCaptchaHandler().getCooldown().containsKey(player.getUniqueId()) && zCaptcha.getInstance().getCaptchaHandler().getRequiresAuth().containsKey(player.getUniqueId())) {
            String key = zCaptcha.getInstance().getCaptchaHandler().generateAccessKey(player);

            player.sendMessage(ColorUtil.format(zCaptchaConfig.CAPTCHA_REQUIRED.replace("%key%", key)));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        if (zCaptcha.getInstance().getCaptchaHandler().getCooldown().containsKey(player.getUniqueId()) && zCaptcha.getInstance().getCaptchaHandler().getRequiresAuth().containsKey(player.getUniqueId())) {
            String key = zCaptcha.getInstance().getCaptchaHandler().generateAccessKey(player);

            player.sendMessage(ColorUtil.format(zCaptchaConfig.CAPTCHA_REQUIRED.replace("%key%", key)));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (zCaptcha.getInstance().getCaptchaHandler().getCooldown().containsKey(player.getUniqueId()) && zCaptcha.getInstance().getCaptchaHandler().getRequiresAuth().containsKey(player.getUniqueId())) {
            String key = zCaptcha.getInstance().getCaptchaHandler().generateAccessKey(player);

            player.sendMessage(ColorUtil.format(zCaptchaConfig.CAPTCHA_REQUIRED.replace("%key%", key)));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (zCaptcha.getInstance().getCaptchaHandler().getCooldown().containsKey(player.getUniqueId()) && zCaptcha.getInstance().getCaptchaHandler().getRequiresAuth().containsKey(player.getUniqueId())) {
            zCaptcha.getInstance().getCaptchaHandler().getCooldown().remove(player.getUniqueId());
            zCaptcha.getInstance().getCaptchaHandler().getRequiresAuth().remove(player.getUniqueId());
        }
    }
}
