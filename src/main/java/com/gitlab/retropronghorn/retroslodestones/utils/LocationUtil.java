package com.gitlab.retropronghorn.retroslodestones.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/** Represents a new location util
 * Converts and manages location data
 * @author RetroPronghorn
 * @author https://gitlab.com/retropronghorn/retros-lodestones
 * @version 1.0-SNAPSHOT
 * @since 1.0
 */
public class LocationUtil {

    /**
     * Get a world from Bukkit
     *
     * @param world World string name
     **/
    public static World getWorld(String world) {
        return Bukkit.getWorld(world);
    }

    /**
     * Get a bukkit location from a string
     *
     * @param location location string to parse
     * @return Returns location parsed from string
     **/
    public static Location fromString(String location) {
        String[] locationArray = location.split(",");
        if (locationArray.length != 4) {
            LoggingUtil.error("Invalid location string");
            return null;
        }
        World boundWorld = getWorld(locationArray[0]);
        Location boundLocation = new Location(
                boundWorld,
                Integer.parseInt(locationArray[1].trim()),
                Integer.parseInt(locationArray[2].trim()),
                Integer.parseInt(locationArray[3].trim()));
        return boundLocation;
    }

    /**
     * Convert a bukkit location to string
     *
     * @param location location object to stringify
     * @return Retruns string built from Location
     **/
    public static String toString(Location location) {
        return location.getWorld().getName()+", "+location.getBlockX()+", "+location.getBlockY()+", "+location.getBlockZ();
    }
}
