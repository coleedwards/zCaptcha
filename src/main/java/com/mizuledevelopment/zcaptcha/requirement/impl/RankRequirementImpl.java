package com.mizuledevelopment.zcaptcha.requirement.impl;

import com.mizuledevelopment.zcaptcha.requirement.IRequirement;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class RankRequirementImpl implements IRequirement {

    private Chat chat;

    private String value;

    public RankRequirementImpl(Plugin plugin) {
        chat = Bukkit.getServer().getServicesManager().getRegistration(Chat.class).getProvider();
        this.value = plugin.getConfig().getString("requirements." + getRequirementKey());
    }

    @Override
    public String getRequirementKey() {
        return "rank";
    }

    @Override
    public boolean meetsRequirement(Player player) {
        return chat.getPrimaryGroup(player).equalsIgnoreCase(value);
    }
}
