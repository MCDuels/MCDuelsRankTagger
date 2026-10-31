package com.mcduelstagger;

import com.mcduelstagger.compat.KeyMappings;
import com.mcduelstagger.compat.Toasts;
import com.mcduelstagger.config.ConfigHolder;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;

public final class Keybind {
    private static KeyMapping TOGGLE;

    private Keybind() {}

    public static void register() {
        TOGGLE = KeyMappings.registerUnbound("key.mcduelstagger.toggle");
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (TOGGLE.consumeClick()) onToggle(client);
        });
    }

    private static void onToggle(Minecraft client) {
        var cfg = ConfigHolder.get();
        cfg.enabled = !cfg.enabled;
        // Save asynchronously so spamming the keybind doesn't block the render thread on FS I/O.
        ModEntry.scheduleConfigSave();
        SystemToast.add(Toasts.manager(client),
            SystemToast.SystemToastId.PERIODIC_NOTIFICATION,
            Component.translatable(cfg.enabled ? "toast.mcduelstagger.on" : "toast.mcduelstagger.off"),
            Component.empty());
    }
}
