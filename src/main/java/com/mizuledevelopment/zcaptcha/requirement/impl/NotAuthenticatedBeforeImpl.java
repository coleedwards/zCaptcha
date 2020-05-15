package com.mizuledevelopment.zcaptcha.requirement.impl;

import com.mizuledevelopment.zcaptcha.CaptchaPlugin;
import com.mizuledevelopment.zcaptcha.requirement.IRequirement;
import org.bukkit.entity.Player;

public class NotAuthenticatedBeforeImpl implements IRequirement {
    @Override
    public String getRequirementKey() {
        return "not_authenticated_before";
    }

    @Override
    public boolean meetsRequirement(Player player) {
        return !CaptchaPlugin.getInstance().getStorage().hasUserAuthenticated(player.getUniqueId());
    }
}
