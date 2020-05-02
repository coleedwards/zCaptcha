package com.mizuledevelopment.zcaptcha.util;

import org.bukkit.ChatColor;

public class ColorUtil {

    public static String format(String in) {
        return ChatColor.translateAlternateColorCodes('&', in);
    }
}
