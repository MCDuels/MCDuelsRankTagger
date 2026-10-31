package com.mcduelstagger.rank;

public final class RankGlyphs {
    private RankGlyphs() {}

    public static String of(Rank rank, Kit kit) {
        return Character.toString(rank.badgeCodepoint()) + Character.toString(kit.iconCodepoint(rank.family()));
    }
}
