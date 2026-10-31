package com.mcduelstagger.pack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class McDuelsServersTest {
    @Test void recognisesTheApexDomainAndSubdomains() {
        assertTrue(McDuelsServers.isMcDuelsAddress("mcduels.com"));
        assertTrue(McDuelsServers.isMcDuelsAddress("play.mcduels.com"));
        assertTrue(McDuelsServers.isMcDuelsAddress("na2.mcduels.com"));
    }
    @Test void ignoresPortCaseTrailingDotAndWhitespace() {
        assertTrue(McDuelsServers.isMcDuelsAddress(" Play.MCDuels.com.:25565 "));
    }
    @Test void rejectsLookalikeDomains() {
        assertFalse(McDuelsServers.isMcDuelsAddress("notmcduels.com"));
        assertFalse(McDuelsServers.isMcDuelsAddress("mcduels.com.evil.net"));
        assertFalse(McDuelsServers.isMcDuelsAddress("hypixel.net"));
    }
    @Test void rejectsBlankAndIpv6Addresses() {
        assertFalse(McDuelsServers.isMcDuelsAddress(null));
        assertFalse(McDuelsServers.isMcDuelsAddress(""));
        assertFalse(McDuelsServers.isMcDuelsAddress("[::1]:25565"));
    }
    @Test void recognisesTheMcduelsPackReleaseUrl() {
        assertTrue(McDuelsServers.isMcDuelsPackUrl(
            "https://github.com/MCDuels/mcduelspack/releases/latest/download/mcduelspack.zip"));
    }
    @Test void rejectsOtherPackUrls() {
        assertFalse(McDuelsServers.isMcDuelsPackUrl("https://github.com/someone/mcduelspack-fork/pack.zip"));
        assertFalse(McDuelsServers.isMcDuelsPackUrl("https://example.com/pack.zip"));
        assertFalse(McDuelsServers.isMcDuelsPackUrl(null));
    }
}
