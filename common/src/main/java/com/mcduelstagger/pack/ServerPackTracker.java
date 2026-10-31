package com.mcduelstagger.pack;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class ServerPackTracker {
    private final Set<UUID> mcDuelsPackIds = ConcurrentHashMap.newKeySet();
    private final Set<UUID> acceptedPackIds = ConcurrentHashMap.newKeySet();

    public void onOffered(UUID packId, String packUrl, String serverAddress) {
        if (packId == null) return;
        if (McDuelsServers.isMcDuelsPackUrl(packUrl) || McDuelsServers.isMcDuelsAddress(serverAddress)) {
            mcDuelsPackIds.add(packId);
        }
    }

    public void onClientAction(UUID packId, String actionName) {
        if (packId == null) return;
        PackResponse.fromClientAction(actionName).ifPresent(response -> {
            if (response == PackResponse.ACCEPTED) acceptedPackIds.add(packId);
            else acceptedPackIds.remove(packId);
        });
    }

    public void onRemoved(Optional<UUID> packId) {
        if (packId.isPresent()) {
            mcDuelsPackIds.remove(packId.get());
            acceptedPackIds.remove(packId.get());
        } else {
            reset();
        }
    }

    public void reset() {
        mcDuelsPackIds.clear();
        acceptedPackIds.clear();
    }

    public boolean isMcDuelsPackAccepted() {
        for (UUID packId : mcDuelsPackIds) {
            if (acceptedPackIds.contains(packId)) return true;
        }
        return false;
    }
}
