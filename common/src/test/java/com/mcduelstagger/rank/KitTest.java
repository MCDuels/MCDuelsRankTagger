package com.mcduelstagger.rank;

import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class KitTest {
    @Test void allNineActiveKitsMapFromApi() {
        assertEquals(Kit.AXE,       Kit.fromApi("axe").orElseThrow());
        assertEquals(Kit.CRYSTAL,   Kit.fromApi("crystal").orElseThrow());
        assertEquals(Kit.MACE,      Kit.fromApi("mace").orElseThrow());
        assertEquals(Kit.NETHOP,    Kit.fromApi("nethop").orElseThrow());
        assertEquals(Kit.POT,       Kit.fromApi("pot").orElseThrow());
        assertEquals(Kit.SMP,       Kit.fromApi("smp").orElseThrow());
        assertEquals(Kit.SPEARMACE, Kit.fromApi("spear_mace").orElseThrow());
        assertEquals(Kit.SWORD,     Kit.fromApi("sword").orElseThrow());
        assertEquals(Kit.UHC,       Kit.fromApi("uhc").orElseThrow());
    }
    @Test void spearmaceWithoutUnderscoreIsRejected() {
        // The API id is "spear_mace" (with underscore). The bare "spearmace" must not match.
        assertEquals(Optional.empty(), Kit.fromApi("spearmace"));
    }
    @Test void spearMaceUsesItsOwnTieredIcons() {
        assertEquals(0xE82D, Kit.SPEARMACE.iconCodepoint(RankFamily.DIAMOND));
        assertEquals(0xE82E, Kit.SPEARMACE.iconCodepoint(RankFamily.DUELER));
        assertEquals(0xE82F, Kit.SPEARMACE.iconCodepoint(RankFamily.GOLD));
        assertEquals(0xE830, Kit.SPEARMACE.iconCodepoint(RankFamily.IRON));
        assertEquals(0xE831, Kit.SPEARMACE.iconCodepoint(RankFamily.SILVER));
    }
    @Test void crystalUsesTheVanillaIconSet() {
        assertEquals(0xE829, Kit.CRYSTAL.iconCodepoint(RankFamily.DUELER));
    }
    @Test void maceSharesTheSmpIconSetLikeTheServer() {
        for (RankFamily family : RankFamily.values()) {
            assertEquals(Kit.SMP.iconCodepoint(family), Kit.MACE.iconCodepoint(family));
        }
    }
    @Test void everyKitIconStaysInsideThePackRankRange() {
        for (Kit kit : Kit.values()) {
            for (RankFamily family : RankFamily.values()) {
                int codepoint = kit.iconCodepoint(family);
                assertTrue(codepoint >= 0xE80A && codepoint <= 0xE831, kit + " " + family);
            }
        }
    }
    @Test void crystalAbbrevIsCpvp() {
        assertEquals("cpvp", Kit.CRYSTAL.abbrev());
    }
    @Test void unknownReturnsEmpty() {
        assertEquals(Optional.empty(), Kit.fromApi("unknown"));
        assertEquals(Optional.empty(), Kit.fromApi(null));
    }
}
