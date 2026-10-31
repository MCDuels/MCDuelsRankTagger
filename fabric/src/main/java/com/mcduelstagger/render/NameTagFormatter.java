package com.mcduelstagger.render;

import com.mcduelstagger.compat.RankFont;
import com.mcduelstagger.rank.RankGlyphs;
import com.mcduelstagger.rank.TierPicker;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public final class NameTagFormatter {
    private NameTagFormatter() {}

    public static MutableComponent prefix(TierPicker.Result result) {
        String glyphs = RankGlyphs.of(result.rank(), result.kit());
        return Component.empty()
            .append(Component.literal(glyphs).setStyle(RankFont.STYLE.withColor(ChatFormatting.WHITE)))
            .append(Component.literal(" "));
    }
}
