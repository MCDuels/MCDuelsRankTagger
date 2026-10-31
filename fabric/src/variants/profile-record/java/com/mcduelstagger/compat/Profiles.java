package com.mcduelstagger.compat;

import com.mojang.authlib.GameProfile;

import java.util.UUID;

public final class Profiles {
    private Profiles() {}

    public static String name(GameProfile profile) { return profile.name(); }
    public static UUID id(GameProfile profile)     { return profile.id(); }
}
