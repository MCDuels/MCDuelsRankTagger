package com.mcduelstagger.rank;

import java.util.Optional;

public enum Kit {
    AXE(      "axe",        "axe",    0xE80A),
    CRYSTAL(  "crystal",    "cpvp",   0xE828),
    MACE(     "mace",       "mace",   0xE819),
    NETHOP(   "nethop",     "nethop", 0xE80F),
    POT(      "pot",        "pot",    0xE814),
    SMP(      "smp",        "smp",    0xE819),
    SPEARMACE("spear_mace", "smace",  0xE82D),
    SWORD(    "sword",      "sword",  0xE81E),
    UHC(      "uhc",        "uhc",    0xE823);

    private final String apiId;
    private final String abbrev;
    private final int iconBaseCodepoint;

    Kit(String apiId, String abbrev, int iconBaseCodepoint) {
        this.apiId = apiId;
        this.abbrev = abbrev;
        this.iconBaseCodepoint = iconBaseCodepoint;
    }

    public String apiId()  { return apiId; }
    public String abbrev() { return abbrev; }

    public int iconCodepoint(RankFamily family) {
        return iconBaseCodepoint + family.kitIconOffset();
    }

    public static Optional<Kit> fromApi(String apiId) {
        if (apiId == null) return Optional.empty();
        for (Kit k : values()) if (k.apiId.equals(apiId)) return Optional.of(k);
        return Optional.empty();
    }
}
