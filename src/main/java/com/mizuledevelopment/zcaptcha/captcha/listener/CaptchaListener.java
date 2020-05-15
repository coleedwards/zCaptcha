package com.mizuledevelopment.zcaptcha.captcha.listener;

import com.mizuledevelopment.zcaptcha.CaptchaPlugin;
import com.mizuledevelopment.zcaptcha.util.ColorUtil;
import com.mizuledevelopment.zcaptcha.CaptchaPluginConstants;
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
        if (CaptchaPlugin.getInstance().getRequirementHandler().requiresCaptcha(event.getPlayer())) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    String key = CaptchaPlugin.getInstance().getCaptchaHandler().generateAccessKey(player);

                    if (key != null) {
                        player.sendMessage(ColorUtil.format(CaptchaPluginConstants.CAPTCHA_REQUIRED.replace("%key%", key)));
                    }
                }
            }.runTaskLater(CaptchaPlugin.getInstance(), 1);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockY() == event.getTo().getBlockY() && event.getFrom().getBlockZ() == event.getTo().getBlockZ()) {
            return;
        }

        Player player = event.getPlayer();

        if (CaptchaPlugin.getInstance().getCaptchaHandler().getCooldown().containsKey(player.getUniqueId()) && CaptchaPlugin.getInstance().getCaptchaHandler().getRequiresAuth().containsKey(player.getUniqueId())) {
            event.setTo(event.getFrom());

            String key = CaptchaPlugin.getInstance().getCaptchaHandler().generateAccessKey(player);

            player.sendMessage(ColorUtil.format(CaptchaPluginConstants.CAPTCHA_REQUIRED.replace("%key%", key)));
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (CaptchaPlugin.getInstance().getCaptchaHandler().getCooldown().containsKey(player.getUniqueId()) && CaptchaPlugin.getInstance().getCaptchaHandler().getRequiresAuth().containsKey(player.getUniqueId())) {
            if (!event.getMessage().equalsIgnoreCase("/checkauth")) {
                String key = CaptchaPlugin.getInstance().getCaptchaHandler().generateAccessKey(player);

                player.sendMessage(ColorUtil.format(CaptchaPluginConstants.CAPTCHA_REQUIRED.replace("%key%", key)));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (CaptchaPlugin.getInstance().getCaptchaHandler().getCooldown().containsKey(player.getUniqueId()) && CaptchaPlugin.getInstance().getCaptchaHandler().getRequiresAuth().containsKey(player.getUniqueId())) {
            String key = CaptchaPlugin.getInstance().getCaptchaHandler().generateAccessKey(player);

            player.sendMessage(ColorUtil.format(CaptchaPluginConstants.CAPTCHA_REQUIRED.replace("%key%", key)));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        if (CaptchaPlugin.getInstance().getCaptchaHandler().getCooldown().containsKey(player.getUniqueId()) && CaptchaPlugin.getInstance().getCaptchaHandler().getRequiresAuth().containsKey(player.getUniqueId())) {
            String key = CaptchaPlugin.getInstance().getCaptchaHandler().generateAccessKey(player);

            player.sendMessage(ColorUtil.format(CaptchaPluginConstants.CAPTCHA_REQUIRED.replace("%key%", key)));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (CaptchaPlugin.getInstance().getCaptchaHandler().getCooldown().containsKey(player.getUniqueId()) && CaptchaPlugin.getInstance().getCaptchaHandler().getRequiresAuth().containsKey(player.getUniqueId())) {
            String key = CaptchaPlugin.getInstance().getCaptchaHandler().generateAccessKey(player);

            player.sendMessage(ColorUtil.format(CaptchaPluginConstants.CAPTCHA_REQUIRED.replace("%key%", key)));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (CaptchaPlugin.getInstance().getCaptchaHandler().getCooldown().containsKey(player.getUniqueId()) && CaptchaPlugin.getInstance().getCaptchaHandler().getRequiresAuth().containsKey(player.getUniqueId())) {
            CaptchaPlugin.getInstance().getCaptchaHandler().getCooldown().remove(player.getUniqueId());
            CaptchaPlugin.getInstance().getCaptchaHandler().getRequiresAuth().remove(player.getUniqueId());
        }
    }
}
