package com.mcduelstagger.rank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RankGlyphsTest {
    @Test void highDuelerSpearMaceIsBadgeThenDuelerSpearMaceIcon() {
        assertEquals("", RankGlyphs.of(Rank.HD, Kit.SPEARMACE));
    }
    @Test void goldSwordIsGoldBadgeThenGoldSwordIcon() {
        assertEquals("", RankGlyphs.of(Rank.G, Kit.SWORD));
    }
    @Test void highIronCrystalUsesIronVanillaIcon() {
        assertEquals("", RankGlyphs.of(Rank.HI, Kit.CRYSTAL));
    }
}
