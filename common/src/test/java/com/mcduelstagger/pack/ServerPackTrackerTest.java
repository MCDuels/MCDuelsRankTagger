package com.mcduelstagger.pack;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServerPackTrackerTest {
    private static final String PACK_URL =
        "https://github.com/MCDuels/mcduelspack/releases/latest/download/mcduelspack.zip";
    private final UUID packId = UUID.randomUUID();

    @Test void nothingOfferedMeansNoMcduelsPack() {
        assertFalse(new ServerPackTracker().isMcDuelsPackAccepted());
    }
    @Test void acceptingTheMcduelsPackOnAnyAddressCounts() {
        var tracker = new ServerPackTracker();
        tracker.onOffered(packId, PACK_URL, "203.0.113.7:25565");
        tracker.onClientAction(packId, "ACCEPTED");
        assertTrue(tracker.isMcDuelsPackAccepted());
    }
    @Test void acceptingAnyPackOnAnMcduelsHostCounts() {
        var tracker = new ServerPackTracker();
        tracker.onOffered(packId, "https://cdn.example.com/pack.zip", "play.mcduels.com");
        tracker.onClientAction(packId, "SUCCESSFULLY_LOADED");
        assertTrue(tracker.isMcDuelsPackAccepted());
    }
    @Test void decliningTheMcduelsPackKeepsTagsOn() {
        var tracker = new ServerPackTracker();
        tracker.onOffered(packId, PACK_URL, "play.mcduels.com");
        tracker.onClientAction(packId, "DECLINED");
        assertFalse(tracker.isMcDuelsPackAccepted());
    }
    @Test void aFailedDownloadAfterAcceptingKeepsTagsOn() {
        var tracker = new ServerPackTracker();
        tracker.onOffered(packId, PACK_URL, "play.mcduels.com");
        tracker.onClientAction(packId, "ACCEPTED");
        tracker.onClientAction(packId, "FAILED_DOWNLOAD");
        assertFalse(tracker.isMcDuelsPackAccepted());
    }
    @Test void acceptingAnotherServersPackDoesNotCount() {
        var tracker = new ServerPackTracker();
        tracker.onOffered(packId, "https://example.com/pack.zip", "hypixel.net");
        tracker.onClientAction(packId, "SUCCESSFULLY_LOADED");
        assertFalse(tracker.isMcDuelsPackAccepted());
    }
    @Test void removingThePackTurnsTagsBackOn() {
        var tracker = new ServerPackTracker();
        tracker.onOffered(packId, PACK_URL, "play.mcduels.com");
        tracker.onClientAction(packId, "SUCCESSFULLY_LOADED");
        tracker.onRemoved(Optional.of(packId));
        assertFalse(tracker.isMcDuelsPackAccepted());
    }
    @Test void removingAllPacksTurnsTagsBackOn() {
        var tracker = new ServerPackTracker();
        tracker.onOffered(packId, PACK_URL, "play.mcduels.com");
        tracker.onClientAction(packId, "SUCCESSFULLY_LOADED");
        tracker.onRemoved(Optional.empty());
        assertFalse(tracker.isMcDuelsPackAccepted());
    }
    @Test void removingAnUnrelatedPackKeepsTheMcduelsPack() {
        var tracker = new ServerPackTracker();
        tracker.onOffered(packId, PACK_URL, "play.mcduels.com");
        tracker.onClientAction(packId, "SUCCESSFULLY_LOADED");
        tracker.onRemoved(Optional.of(UUID.randomUUID()));
        assertTrue(tracker.isMcDuelsPackAccepted());
    }
    @Test void resetForgetsEverything() {
        var tracker = new ServerPackTracker();
        tracker.onOffered(packId, PACK_URL, "play.mcduels.com");
        tracker.onClientAction(packId, "SUCCESSFULLY_LOADED");
        tracker.reset();
        assertFalse(tracker.isMcDuelsPackAccepted());
    }
    @Test void nullPackIdsAreIgnored() {
        var tracker = new ServerPackTracker();
        tracker.onOffered(null, PACK_URL, "play.mcduels.com");
        tracker.onClientAction(null, "ACCEPTED");
        assertFalse(tracker.isMcDuelsPackAccepted());
    }
}
