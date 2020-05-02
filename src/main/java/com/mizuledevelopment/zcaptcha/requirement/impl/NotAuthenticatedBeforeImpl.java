package com.mizuledevelopment.zcaptcha.requirement.impl;

import com.mizuledevelopment.zcaptcha.requirement.IRequirement;
import com.mizuledevelopment.zcaptcha.zCaptcha;
import org.bukkit.entity.Player;

public class NotAuthenticatedBeforeImpl implements IRequirement {
    @Override
    public String getRequirementKey() {
        return "not_authenticated_before";
    }

    @Override
    public boolean meetsRequirement(Player player) {
        return zCaptcha.getInstance().getStorage().hasUserAuthenticated(player.getUniqueId());
    }
}
