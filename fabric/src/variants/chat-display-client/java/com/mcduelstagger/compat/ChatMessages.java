package com.mcduelstagger.compat;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public final class ChatMessages {
    private ChatMessages() {}

    public static void show(LocalPlayer player, Component message) {
        player.displayClientMessage(message, false);
    }
}
