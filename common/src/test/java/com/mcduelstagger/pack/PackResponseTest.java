package com.mcduelstagger.pack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PackResponseTest {
    @ParameterizedTest
    @ValueSource(strings = {"ACCEPTED", "DOWNLOADED", "SUCCESSFULLY_LOADED"})
    void acceptingActionsCountAsAccepted(String action) {
        assertEquals(Optional.of(PackResponse.ACCEPTED), PackResponse.fromClientAction(action));
    }

    @ParameterizedTest
    @ValueSource(strings = {"DECLINED", "FAILED_DOWNLOAD", "INVALID_URL", "FAILED_RELOAD", "DISCARDED"})
    void decliningOrFailingActionsCountAsRejected(String action) {
        assertEquals(Optional.of(PackResponse.REJECTED), PackResponse.fromClientAction(action));
    }

    @Test void unknownActionsAreIgnored() {
        assertEquals(Optional.empty(), PackResponse.fromClientAction("SOMETHING_NEW"));
        assertEquals(Optional.empty(), PackResponse.fromClientAction(null));
    }
}
