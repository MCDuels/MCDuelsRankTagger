package com.mcduelstagger;

import com.mcduelstagger.cache.CacheEntry;
import com.mcduelstagger.cache.CacheStatus;
import com.mcduelstagger.cache.PlayerRankCache;
import com.mcduelstagger.compat.ChatMessages;
import com.mcduelstagger.compat.ClientCommandNodes;
import com.mcduelstagger.compat.Profiles;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/** Dev-time client command: /mcduelstagger lookup <name>. Useful for smoke-testing the cache + API path. */
public final class DebugCommand {
    private DebugCommand() {}

    public static void register(PlayerRankCache cache) {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
            dispatcher.register(ClientCommandNodes.literal("mcduelstagger")
                .then(ClientCommandNodes.literal("lookup")
                    .then(ClientCommandNodes.argument("name", StringArgumentType.string())
                        .executes(ctx -> {
                            String name = StringArgumentType.getString(ctx, "name");
                            UUID uuid = resolveUuid(name);

                            // Cache hit on the spot — print and done.
                            var existing = cache.get(uuid);
                            if (existing.isPresent()) {
                                ctx.getSource().sendFeedback(Component.literal(formatEntry(existing.get())));
                                return 1;
                            }

                            // Cache miss: kick off the fetch, then poll briefly for the result.
                            ctx.getSource().sendFeedback(Component.literal("Looking up " + name + "..."));
                            var svc = ModEntry.lookupService();
                            if (svc == null) {
                                ctx.getSource().sendFeedback(Component.literal("Service not ready."));
                                return 0;
                            }
                            svc.lookup(uuid, name);

                            // Wait for the in-flight future off the render thread, then post the
                            // result back on the render thread (chat must be sent there).
                            Thread waiter = new Thread(() -> {
                                try {
                                    svc.awaitInFlight(uuid, 10_000);
                                } catch (Exception ignored) {
                                    // Timed out or threw — fall through to read whatever's cached.
                                }
                                Minecraft mc = Minecraft.getInstance();
                                mc.execute(() -> {
                                    LocalPlayer self = mc.player;
                                    if (self == null) return;
                                    String msg = cache.get(uuid)
                                        .map(DebugCommand::formatEntry)
                                        .orElse("Lookup timed out for " + name + ".");
                                    ChatMessages.show(self, Component.literal(msg));
                                });
                            }, "mcduelstagger-lookup-waiter");
                            waiter.setDaemon(true);
                            waiter.start();
                            return 1;
                        })))));
    }

    private static String formatEntry(CacheEntry e) {
        if (e.status() == CacheStatus.HIT) {
            // Use the user-facing display strings (e.g. "HDi" / "Di") and the kit's
            // short MCDuels label (e.g. "cpvp"), not raw enum names ("HDI" / "CRYSTAL").
            return "Cached HIT: " + e.rank().display() + " " + e.kit().abbrev();
        }
        if (e.status() == CacheStatus.MISS) return "Player not found on MCDuels (cached MISS).";
        return "Cached " + e.status();
    }

    private static UUID resolveUuid(String name) {
        var connection = Minecraft.getInstance().getConnection();
        if (connection != null) {
            for (PlayerInfo p : connection.getOnlinePlayers()) {
                if (Profiles.name(p.getProfile()).equalsIgnoreCase(name)) {
                    return Profiles.id(p.getProfile());
                }
            }
        }
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8));
    }
}
