package com.mizuledevelopment.zcaptcha.requirement.impl;

import com.mizuledevelopment.zcaptcha.requirement.IRequirement;
import lombok.Getter;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

@Getter
public class RankRequirementImpl implements IRequirement {

    private Chat chat;

    private String value;

    public RankRequirementImpl(Plugin plugin) {
        RegisteredServiceProvider<Chat> chatRegisteredServiceProvider = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
        if (chatRegisteredServiceProvider != null) {
            chat = chatRegisteredServiceProvider.getProvider();
        }
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
