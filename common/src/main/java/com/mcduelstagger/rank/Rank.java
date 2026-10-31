package com.mcduelstagger.rank;

import java.util.Optional;

public enum Rank {
    HD ("high_dueler",   1, "HD",  RankFamily.DUELER,  0xE804),
    D  ("dueler",        2, "D",   RankFamily.DUELER,  0xE801),
    HDI("high_diamond",  3, "HDi", RankFamily.DIAMOND, 0xE803),
    DI ("diamond",       4, "Di",  RankFamily.DIAMOND, 0xE800),
    HG ("high_gold",     5, "HG",  RankFamily.GOLD,    0xE805),
    G  ("gold",          6, "G",   RankFamily.GOLD,    0xE802),
    HS ("high_silver",   7, "HS",  RankFamily.SILVER,  0xE807),
    S  ("silver",        8, "S",   RankFamily.SILVER,  0xE809),
    HI ("high_iron",     9, "HI",  RankFamily.IRON,    0xE806),
    I  ("iron",         10, "I",   RankFamily.IRON,    0xE808);

    private final String apiString;
    private final int tier;
    private final String display;
    private final RankFamily family;
    private final int badgeCodepoint;

    Rank(String apiString, int tier, String display, RankFamily family, int badgeCodepoint) {
        this.apiString = apiString;
        this.tier = tier;
        this.display = display;
        this.family = family;
        this.badgeCodepoint = badgeCodepoint;
    }

    public String apiString()      { return apiString; }
    public int tier()              { return tier; }
    public String display()        { return display; }
    public RankFamily family()     { return family; }
    public int badgeCodepoint()    { return badgeCodepoint; }

    public static Optional<Rank> fromApi(String apiString) {
        if (apiString == null) return Optional.empty();
        for (Rank r : values()) if (r.apiString.equals(apiString)) return Optional.of(r);
        return Optional.empty();
    }
}
