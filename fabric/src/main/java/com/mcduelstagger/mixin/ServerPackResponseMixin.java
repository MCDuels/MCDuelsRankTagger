package com.mcduelstagger.mixin;

import com.mcduelstagger.ModEntry;
import net.minecraft.network.protocol.common.ServerboundResourcePackPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(ServerboundResourcePackPacket.class)
public abstract class ServerPackResponseMixin {

    @Inject(
        method = "<init>(Ljava/util/UUID;Lnet/minecraft/network/protocol/common/ServerboundResourcePackPacket$Action;)V",
        at = @At("RETURN"))
    private void mcduelstagger$recordResponse(UUID packId, ServerboundResourcePackPacket.Action action, CallbackInfo ci) {
        ModEntry.serverPacks().onClientAction(packId, action.name());
    }
}
