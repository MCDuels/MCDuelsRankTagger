package com.mcduelstagger.compat;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;

public final class KeyMappings {
    private KeyMappings() {}

    public static KeyMapping registerUnbound(String translationKey) {
        return KeyMappingHelper.registerKeyMapping(new KeyMapping(
            translationKey, InputConstants.UNKNOWN.getValue(), KeyMapping.Category.MISC));
    }
}
