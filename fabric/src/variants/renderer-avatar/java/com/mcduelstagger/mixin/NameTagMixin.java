package com.mcduelstagger.mixin;

import com.mcduelstagger.render.NameTagDecorator;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public abstract class NameTagMixin {

    @Inject(
        method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V",
        at = @At("TAIL"))
    private void mcduelstagger$prefixNameTag(Avatar avatar, AvatarRenderState state, float partialTick, CallbackInfo ci) {
        if (!(avatar instanceof Player player)) return;
        state.nameTag = NameTagDecorator.decorate(player, state.nameTag, state.isCrouching);
    }
}
