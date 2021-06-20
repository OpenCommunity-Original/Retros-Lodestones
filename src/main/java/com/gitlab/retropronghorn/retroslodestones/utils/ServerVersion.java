package com.gitlab.retropronghorn.retroslodestones.utils;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;


/** Represents a ServerVersion
 * @author Songoda
 * @author https://gitlab.com/Songoda/songodaupdater/-/blob/master/Core/src/main/java/com/songoda/core/compatibility/ServerVersion.java
 * @version 1.0-SNAPSHOT
 * @since 1.0
 */
public enum ServerVersion {

    UNKNOWN, V1_7, V1_8, V1_9, V1_10, V1_11, V1_12, V1_13, V1_14, V1_15, V1_16, V1_17, V1_18, V1_19, V1_20;

    private final static String serverPackagePath = Bukkit.getServer().getClass().getPackage().getName();
    private final static String serverPackageVersion = serverPackagePath.substring(serverPackagePath.lastIndexOf('.') + 1);
    private final static String serverReleaseVersion = serverPackageVersion.indexOf('R') != -1 ? serverPackageVersion.substring(serverPackageVersion.indexOf('R') + 1) : "";
    private final static ServerVersion serverVersion = getVersion();

    /**
     * Get version of current server
     *
     * @return returns server version
     **/
    private static ServerVersion getVersion() {
        for (ServerVersion version : values()) {
            if (serverPackageVersion.toUpperCase().startsWith(version.name())) {
                return version;
            }
        }
        return UNKNOWN;
    }

    /**
     * Is version less then server version
     * 
     * @param other Server version to compare against
     *
     * @return returns wether or not the provided version is greater than the server version
     **/
    public boolean isLessThan(ServerVersion other) {
        return this.ordinal() < other.ordinal();
    }
    
    /**
     * Is version greater than or equal to server
     * 
     * @param other Server version to compare against
     * 
     * @return returns wether a provided version is at or above server version
     **/
    public boolean isAtOrBelow(ServerVersion other) {
        return this.ordinal() <= other.ordinal();
    }

    /**
     * Is server greater than provided server 
     * 
     * @param other Server version to compare against
     * 
     * @return returns wether a provided version is below server version
     **/
    public boolean isGreaterThan(ServerVersion other) {
        return this.ordinal() > other.ordinal();
    }

    /**
     * Is server greater than or equal to provided version 
     * 
     * @param other Server version to compare against
     * 
     * @return returns wether a provided version is at or less than or equal to server version
     **/
    public boolean isAtLeast(ServerVersion other) {
        return this.ordinal() >= other.ordinal();
    }

    
    /**
     * Get the string version of the server
     * 
     * @return returns the Bukkit server version
     **/
    public static String getServerVersionString() {
        return serverPackageVersion;
    }

    
    /**
     * Get server release version number
     * 
     * @return returns wether a provided version is at or less than or equal to server version
     **/
    public static String getVersionReleaseNumber() {
        return serverReleaseVersion;
    }

    /**
     * Get the version of the server
     * 
     * @return server version
     **/
    public static ServerVersion getServerVersion() {
        return serverVersion;
    }

    /**
     * Check if server version meets our bukkit server version
     * 
     * @return returns wether a provided version is at bukkit server version
     **/
    public static boolean isServerVersion(ServerVersion version) {
        return serverVersion == version;
    }

    /**
     * Check a array of versions contains server version
     * 
     * @return returns wether an array of versions contains server version
     **/
    public static boolean isServerVersion(ServerVersion... versions) {
        return ArrayUtils.contains(versions, serverVersion);
    }

    /**
     * Check a array of versions contains server version
     * 
     * @return returns wether an array of versions contains server version
     **/
    public static boolean isServerVersionAbove(ServerVersion version) {
        return serverVersion.ordinal() > version.ordinal();
    }

    /**
     * Check if the bukkit server version is at least the provided version
     * 
     * @return returns wether the bukkit server version is at least the provided version
     **/
    public static boolean isServerVersionAtLeast(ServerVersion version) {
        return serverVersion.ordinal() >= version.ordinal();
    }

    /**
     * Check if the bukkit server version is at or below the provided version
     * 
     * @return returns wether the bukkit server version is at or below the provided version
     **/
    public static boolean isServerVersionAtOrBelow(ServerVersion version) {
        return serverVersion.ordinal() <= version.ordinal();
    }

    /**
     * Check if the bukkit server version is below the provided version
     * 
     * @return returns if the bukkit server version is below the provided version
     **/
    public static boolean isServerVersionBelow(ServerVersion version) {
        return serverVersion.ordinal() < version.ordinal();
    }
}
