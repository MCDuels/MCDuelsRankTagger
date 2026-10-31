package com.mcduelstagger.compat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.ToastManager;

public final class Toasts {
    private Toasts() {}

    public static ToastManager manager(Minecraft client) {
        return client.gui.toastManager();
    }
}
