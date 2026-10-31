package com.mcduelstagger.pack;

import java.util.Locale;

public final class McDuelsServers {
    private static final String DOMAIN = "mcduels.com";
    private static final String PACK_REPOSITORY = "://github.com/mcduels/mcduelspack/";

    private McDuelsServers() {}

    public static boolean isMcDuelsAddress(String address) {
        if (address == null || address.isBlank()) return false;
        String host = hostOf(address.trim().toLowerCase(Locale.ROOT));
        return host.equals(DOMAIN) || host.endsWith("." + DOMAIN);
    }

    public static boolean isMcDuelsPackUrl(String url) {
        return url != null && url.toLowerCase(Locale.ROOT).contains(PACK_REPOSITORY);
    }

    private static String hostOf(String address) {
        String host = address;
        int portSeparator = host.lastIndexOf(':');
        if (portSeparator >= 0 && host.indexOf(':') == portSeparator) host = host.substring(0, portSeparator);
        while (host.endsWith(".")) host = host.substring(0, host.length() - 1);
        return host;
    }
}
