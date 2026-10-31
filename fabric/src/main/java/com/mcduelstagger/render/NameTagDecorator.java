package com.mcduelstagger.render;

import com.mcduelstagger.ModEntry;
import com.mcduelstagger.RankLookupService;
import com.mcduelstagger.config.ConfigHolder;
import com.mcduelstagger.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public final class NameTagDecorator {
    private NameTagDecorator() {}

    public static Component decorate(Player player, Component nameTag, boolean isCrouching) {
        if (nameTag == null) return null;

        ModConfig cfg = ConfigHolder.get();
        if (cfg == null || !cfg.enabled) return nameTag;
        if (isCrouching && !cfg.showOnSneaking) return nameTag;

        Minecraft mc = Minecraft.getInstance();
        if (!cfg.showOnSelf && player == mc.player) return nameTag;
        ClientPacketListener connection = mc.getConnection();
        if (connection == null || connection.getPlayerInfo(player.getUUID()) == null) return nameTag;
        if (ModEntry.serverPacks().isMcDuelsPackAccepted()) return nameTag;

        RankLookupService svc = ModEntry.lookupService();
        if (svc == null) return nameTag;

        return svc.lookup(player.getUUID(), player.getScoreboardName())
            .<Component>map(result -> NameTagFormatter.prefix(result).append(nameTag))
            .orElse(nameTag);
    }
}
