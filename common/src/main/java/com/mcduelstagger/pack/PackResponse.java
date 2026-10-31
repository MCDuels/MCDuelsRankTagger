package com.mcduelstagger.pack;

import java.util.Optional;

public enum PackResponse {
    ACCEPTED,
    REJECTED;

    public static Optional<PackResponse> fromClientAction(String actionName) {
        if (actionName == null) return Optional.empty();
        return switch (actionName) {
            case "ACCEPTED", "DOWNLOADED", "SUCCESSFULLY_LOADED" -> Optional.of(ACCEPTED);
            case "DECLINED", "FAILED_DOWNLOAD", "INVALID_URL", "FAILED_RELOAD", "DISCARDED" -> Optional.of(REJECTED);
            default -> Optional.empty();
        };
    }
}
