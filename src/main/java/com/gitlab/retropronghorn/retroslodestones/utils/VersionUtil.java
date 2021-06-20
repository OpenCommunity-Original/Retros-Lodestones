package com.gitlab.retropronghorn.retroslodestones.utils;

public class VersionUtil {
    final public static ServerVersion LOWEST_VERSION = ServerVersion.V1_8;
    final public static ServerVersion TARGET_VERSION = ServerVersion.V1_16;
    final public static ServerVersion VER_113 = ServerVersion.V1_13;

    /**
     * Check if the current version double is at or above the expected version
     *
     * @param expectedVersion Version we're expecting the server to be run on
     * @deprecated this method can be replaced with ServerVersion.isServerVersionAtLeast
     * @return returns if the server version is at or above the specified version
     **/
    @Deprecated
    public static Boolean isAtOrAbove(ServerVersion version) {
        return ServerVersion.isServerVersionAtLeast(version);
    }

    /**
     * Check if the plugin is running on a version lower than the target
     * if so, let the console know we're running in legacy support mode.
     *
     **/
    public static void legacyCheck() {
        if (!isAtOrAbove(TARGET_VERSION))
            LoggingUtil.info("Server is running a lower than target version, running in legacy mode.");
    }

}
