package com.mcduelstagger.rank;

public enum RankFamily {
    DIAMOND(0),
    DUELER(1),
    GOLD(2),
    IRON(3),
    SILVER(4);

    private final int kitIconOffset;

    RankFamily(int kitIconOffset) {
        this.kitIconOffset = kitIconOffset;
    }

    public int kitIconOffset() { return kitIconOffset; }
}
