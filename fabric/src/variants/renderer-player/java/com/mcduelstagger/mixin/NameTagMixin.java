package com.mcduelstagger.mixin;

import com.mcduelstagger.render.NameTagDecorator;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class NameTagMixin {

    @Inject(
        method = "extractRenderState(Lnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;F)V",
        at = @At("TAIL"))
    private void mcduelstagger$prefixNameTag(AbstractClientPlayer player, PlayerRenderState state, float partialTick,
                                             CallbackInfo ci) {
        state.nameTag = NameTagDecorator.decorate(player, state.nameTag, state.isCrouching);
    }
}
