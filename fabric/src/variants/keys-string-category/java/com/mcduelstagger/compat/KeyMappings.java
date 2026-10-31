package com.mcduelstagger.compat;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

public final class KeyMappings {
    private KeyMappings() {}

    public static KeyMapping registerUnbound(String translationKey) {
        return KeyBindingHelper.registerKeyBinding(new KeyMapping(
            translationKey, InputConstants.UNKNOWN.getValue(), KeyMapping.CATEGORY_MISC));
    }
}
