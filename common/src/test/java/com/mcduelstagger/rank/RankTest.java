package com.mcduelstagger.rank;

import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class RankTest {
    @Test void mapsAllTenApiStrings() {
        assertEquals(Rank.HD, Rank.fromApi("high_dueler").orElseThrow());
        assertEquals(Rank.D, Rank.fromApi("dueler").orElseThrow());
        assertEquals(Rank.HDI, Rank.fromApi("high_diamond").orElseThrow());
        assertEquals(Rank.DI, Rank.fromApi("diamond").orElseThrow());
        assertEquals(Rank.HG, Rank.fromApi("high_gold").orElseThrow());
        assertEquals(Rank.G, Rank.fromApi("gold").orElseThrow());
        assertEquals(Rank.HS, Rank.fromApi("high_silver").orElseThrow());
        assertEquals(Rank.S, Rank.fromApi("silver").orElseThrow());
        assertEquals(Rank.HI, Rank.fromApi("high_iron").orElseThrow());
        assertEquals(Rank.I, Rank.fromApi("iron").orElseThrow());
    }
    @Test void unknownStringsReturnEmpty() {
        assertEquals(Optional.empty(), Rank.fromApi("foo"));
        assertEquals(Optional.empty(), Rank.fromApi(""));
        assertEquals(Optional.empty(), Rank.fromApi(null));
    }
    @Test void displayLabelsMatchMcduelsAbbreviations() {
        assertEquals("HD",  Rank.HD.display());
        assertEquals("D",   Rank.D.display());
        assertEquals("HDi", Rank.HDI.display());
        assertEquals("Di",  Rank.DI.display());
        assertEquals("HG",  Rank.HG.display());
        assertEquals("G",   Rank.G.display());
        assertEquals("HS",  Rank.HS.display());
        assertEquals("S",   Rank.S.display());
        assertEquals("HI",  Rank.HI.display());
        assertEquals("I",   Rank.I.display());
    }
    @Test void rankOrderingHigherIsBetter() {
        assertTrue(Rank.HD.tier() < Rank.D.tier());
        assertTrue(Rank.D.tier() < Rank.HDI.tier());
        assertTrue(Rank.HI.tier() < Rank.I.tier());
    }
    @Test void badgesUseTheMcduelsPackCodepoints() {
        assertEquals(0xE804, Rank.HD.badgeCodepoint());
        assertEquals(0xE801, Rank.D.badgeCodepoint());
        assertEquals(0xE803, Rank.HDI.badgeCodepoint());
        assertEquals(0xE800, Rank.DI.badgeCodepoint());
        assertEquals(0xE805, Rank.HG.badgeCodepoint());
        assertEquals(0xE802, Rank.G.badgeCodepoint());
        assertEquals(0xE807, Rank.HS.badgeCodepoint());
        assertEquals(0xE809, Rank.S.badgeCodepoint());
        assertEquals(0xE806, Rank.HI.badgeCodepoint());
        assertEquals(0xE808, Rank.I.badgeCodepoint());
    }
    @Test void highAndRegularRanksShareAFamily() {
        assertEquals(RankFamily.DUELER, Rank.HD.family());
        assertEquals(RankFamily.DUELER, Rank.D.family());
        assertEquals(RankFamily.DIAMOND, Rank.HDI.family());
        assertEquals(RankFamily.GOLD, Rank.G.family());
        assertEquals(RankFamily.SILVER, Rank.HS.family());
        assertEquals(RankFamily.IRON, Rank.I.family());
    }
}
