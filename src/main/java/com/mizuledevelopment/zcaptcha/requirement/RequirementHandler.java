package com.mizuledevelopment.zcaptcha.requirement;

import com.mizuledevelopment.zcaptcha.requirement.impl.NotAuthenticatedBeforeImpl;
import com.mizuledevelopment.zcaptcha.requirement.impl.RankRequirementImpl;
import com.mizuledevelopment.zcaptcha.zCaptcha;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

@Getter
public class RequirementHandler {

    private Set<IRequirement> requirementsExist = new HashSet<>();
    private Set<IRequirement> requirementsRequired = new HashSet<>();

    public RequirementHandler() {
        requirementsExist.add(new NotAuthenticatedBeforeImpl());

        if (Bukkit.getServer().getPluginManager().isPluginEnabled("Vault")) {
            requirementsExist.add(new RankRequirementImpl(zCaptcha.getInstance()));
        }

        orgLoop:
        for (String key : zCaptcha.getInstance().getConfig().getConfigurationSection("requirements").getKeys(false)) {
            for (IRequirement requirement : requirementsExist) {
                if (requirement.getRequirementKey().equalsIgnoreCase(key)) {
                    if (requirement instanceof RankRequirementImpl && ((RankRequirementImpl) requirement).getChat() == null) {
                        continue;
                    }
                    requirementsRequired.add(requirement);
                    break orgLoop;
                }
            }
        }
    }

    public boolean requiresCaptcha(Player player) {
        for (IRequirement requirement : requirementsRequired) {
            if (!requirement.meetsRequirement(player)) {
                return false;
            }
        }
        return true;
    }

    public void addRequirement(IRequirement requirement) {
        requirementsExist.add(requirement);
        requirementsRequired.add(requirement);
    }

}
