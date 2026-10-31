package com.mcduelstagger.compat;

import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

public final class RankFont {
    public static final Style STYLE = Style.EMPTY.withFont(
        new FontDescription.Resource(ResourceLocation.fromNamespaceAndPath("mcduelstagger", "ranks")));

    private RankFont() {}
}
