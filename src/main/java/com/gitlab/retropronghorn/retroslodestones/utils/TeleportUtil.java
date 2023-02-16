package com.gitlab.retropronghorn.retroslodestones.utils;

import com.gitlab.retropronghorn.retroslodestones.RetrosLodestones;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.PLUGIN;


/**
 * Represents a teleport util
 */
public class TeleportUtil {
    /**
     * Teleport a player to a remote location
     *
     * @param player   player to teleport
     * @param location location to teleport player to
     **/
    public static Boolean teleportPlayer(RetrosLodestones instance, Player player, Location location) {
        Location offsetLocation = new Location(
                location.getWorld(),
                location.getX() + 0.5,
                location.getY() + 1,
                location.getZ() + 0.5);

        instance.getServer().getScheduler().scheduleSyncDelayedTask(instance, () -> player.teleportAsync(offsetLocation, PLUGIN), instance.getConfig().getInt("teleport-delay"));
        return true;
    }

    /**
     * Check for player sized free space above
     *
     * @param location location to check
     * @return retruns if there is free space above a location
     **/
    public static Boolean freeSpaceAbove(Location location) {
        Location oneBlockAbove = new Location(
                location.getWorld(),
                location.getX(),
                location.getY() + 1,
                location.getZ());

        Location twoBlocksAbove = new Location(
                location.getWorld(),
                location.getX(),
                location.getY() + 2,
                location.getZ());

        return oneBlockAbove.getBlock().getType().toString().equalsIgnoreCase("AIR") &&
                twoBlocksAbove.getBlock().getType().toString().equalsIgnoreCase("AIR");
    }
}
