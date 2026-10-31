package com.mcduelstagger.mixin;

import com.mcduelstagger.ModEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.protocol.common.ClientboundResourcePackPopPacket;
import net.minecraft.network.protocol.common.ClientboundResourcePackPushPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientCommonPacketListenerImpl.class)
public abstract class ServerPackOfferMixin {

    @Inject(method = "handleResourcePackPush", at = @At("HEAD"))
    private void mcduelstagger$recordOffer(ClientboundResourcePackPushPacket packet, CallbackInfo ci) {
        ServerData server = Minecraft.getInstance().getCurrentServer();
        ModEntry.serverPacks().onOffered(packet.id(), packet.url(), server == null ? null : server.ip);
    }

    @Inject(method = "handleResourcePackPop", at = @At("HEAD"))
    private void mcduelstagger$recordRemoval(ClientboundResourcePackPopPacket packet, CallbackInfo ci) {
        ModEntry.serverPacks().onRemoved(packet.id());
    }
}
