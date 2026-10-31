package com.mcduelstagger.compat;

import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;

public final class RankFont {
    public static final Style STYLE = Style.EMPTY.withFont(
        new FontDescription.Resource(Identifier.fromNamespaceAndPath("mcduelstagger", "ranks")));

    private RankFont() {}
}
