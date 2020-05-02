package com.mizuledevelopment.zcaptcha.captcha;

import com.mizuledevelopment.zcaptcha.util.ColorUtil;
import com.mizuledevelopment.zcaptcha.zCaptcha;
import com.mizuledevelopment.zcaptcha.zCaptchaConfig;
import lombok.Data;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.*;

@Data
public class CaptchaHandler {

    private Map<UUID, String> requiresAuth = new HashMap<>();
    private Map<UUID, Long> cooldown = new HashMap<>();

    public CaptchaHandler() {
        Bukkit.getServer().getPluginManager().registerEvents(new CaptchaListener(), zCaptcha.getInstance());
        new CaptchaCheckRunnable();
        new CaptchaCooldownRunnable();
    }

    public String generateAccessKey(Player player) {
        if (requiresAuth.containsKey(player.getUniqueId()) && cooldown.containsKey(player.getUniqueId())) {
            return requiresAuth.get(player.getUniqueId());
        }

        HttpPost httpPost = new HttpPost("http://mizuledevelopment.com/captcha/generateAccessKey.php");

        httpPost.setEntity(new UrlEncodedFormEntity(Collections.singleton(new BasicNameValuePair("uuid", player.getUniqueId().toString()))));

        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(httpPost)) {
            requiresAuth.put(player.getUniqueId(), EntityUtils.toString(response.getEntity()));
            cooldown.put(player.getUniqueId(), System.currentTimeMillis()+ zCaptchaConfig.TIME_TO_COMPLETE);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
        }
        return null;
    }

    public boolean checkIfAuthenticated(Player player) {
        HttpPost httpPost = new HttpPost("http://mizuledevelopment.com/captcha/check.php");

        httpPost.setEntity(new UrlEncodedFormEntity(Collections.singleton(new BasicNameValuePair("uuid", player.getUniqueId().toString()))));

        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(httpPost)) {
            String responseStr = EntityUtils.toString(response.getEntity());

            return responseStr.startsWith("AUTHENTICATED_SUCCESS");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void completeCaptcha(Player player) {
        player.sendMessage(ColorUtil.format(zCaptchaConfig.CAPTCHA_COMPLETED));
        requiresAuth.remove(player.getUniqueId());
        cooldown.remove(player.getUniqueId());
        zCaptcha.getInstance().getStorage().addUserAuthenticated(player.getUniqueId());
    }

}
