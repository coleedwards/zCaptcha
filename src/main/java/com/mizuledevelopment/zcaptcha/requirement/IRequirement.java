package com.mizuledevelopment.zcaptcha.requirement;

import org.bukkit.entity.Player;

public interface IRequirement {

    String getRequirementKey();
    boolean meetsRequirement(Player player);

}
