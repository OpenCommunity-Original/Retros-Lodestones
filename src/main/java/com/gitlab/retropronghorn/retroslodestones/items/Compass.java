package com.gitlab.retropronghorn.retroslodestones.items;

import com.gitlab.retropronghorn.retroslodestones.RetrosLodestones;
import com.gitlab.retropronghorn.retroslodestones.handlers.NBTManager;
import com.gitlab.retropronghorn.retroslodestones.utils.*;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


/** Represents a new compass item
 * @author RetroPronghorn
 * @author https://gitlab.com/retropronghorn/retros-lodestones
 * @version 1.0-SNAPSHOT
 * @since 1.0
 */
public class Compass {
    private final RetrosLodestones instance;
    NBTManager nbtManager;

    /**
     * Create a new compass item
     *
     * @param instance Reference to the plugin instance
     **/
    public Compass(RetrosLodestones instance) {
        this.instance = instance;
        nbtManager = new NBTManager(instance);
    }

    /**
     * Determine if a compass is bound to a location
     *
     * @param item Compass item reference to check
     * @return returns wether or not the compass is bound to a location
     **/
    public Boolean isBound(ItemStack item) {
        String locationString = nbtManager.getLocationStringData(item);
        return (locationString != null);
    }

    /**
     * Determine if user is teleporting to somewhere they already are
     *
     * @param location Location we're teleporting to
     * @param player Player that is attempting teleport
     * @return retruns if a teleport would be pointless (are they already there?)
     **/
    public Boolean isTeleportingPointless(Location location, Player player) {
        ItemStack item = ServerVersion.isServerVersionAtOrBelow(ServerVersion.V1_8) ?
            player.getInventory().getItemInHand() : player.getInventory().getItemInMainHand();
        String locationString = LocationUtil.toString(getBoundLocation(item));
        return LocationUtil.toString(location).equalsIgnoreCase(locationString);
    }


    /**
     * Checks if a block is a lodestone
     *
     * @param player Player that is attempting teleport
     * @return returns wether or not bound compass location has lodestone block
     **/
    public Boolean lodestoneMissing(Player player) {
        ItemStack item = ServerVersion.isServerVersionAtOrBelow(ServerVersion.V1_8) ?
            player.getInventory().getItemInHand() : player.getInventory().getItemInMainHand();
        String locationString = LocationUtil.toString(getBoundLocation(item));
        Boolean missingLodestone = LodestoneUtil.isMissing(LocationUtil.fromString(locationString));
        return missingLodestone;
    }

    /**
     * Check requirements and teleport player
     *
     * @param player Player that is attempting teleport
     * @return returns the status of the teleport.
     **/
    public Boolean doTeleport(Player player) {
        ExperienceUtil expUtil = new ExperienceUtil(instance, player);

        ItemStack item = ServerVersion.isServerVersionAtOrBelow(ServerVersion.V1_8) ?
            player.getInventory().getItemInHand() : player.getInventory().getItemInMainHand();
        String locationString = LocationUtil.toString(getBoundLocation(item));
        Double experienceCost = expUtil.calcTeleportCost(
                LocationUtil.fromString(locationString),
                player.getLocation());
        Boolean hasEnoughExp = expUtil.hasEnoughExperience(experienceCost);
        if (hasEnoughExp) {
            expUtil.deductExperience(experienceCost);
            TeleportUtil.teleportPlayer(
                    instance,
                    player,
                    LocationUtil.fromString(locationString));
        }
        return hasEnoughExp;
    }

    /**
     * Bind a compass to a new location
     *
     * @param player Player that is binding the compass
     * @param item Compass item to bind on
     * @param location Location to bind to
     **/
    public void bindCompass(Player player, ItemStack item, Location location) {
        // Remove one compass
        ItemStack singleItem = new ItemStack(item.getType());
        player.getInventory().removeItem(singleItem);
        // Create bound compass
        ItemStack boundCompass = buildCompass(player, item, location);
        if (player.getInventory().firstEmpty() > -1) {
            player.getInventory().addItem(boundCompass);
        } else {
            player.getWorld().dropItem(player.getLocation(), boundCompass);
        }
    }

    /**
     * Build a new compass item with bindings
     *
     * @param player Player that is binding the compass
     * @param item Compass item to bind on
     * @param location Location to bind to
     **/
    private ItemStack buildCompass(Player player, ItemStack item, Location location) {
        ItemStack boundCompass = new ItemStack(item.getType());
        String locationString = LocationUtil.toString(location);
        ItemMeta meta = nbtManager.setCompassMeta(boundCompass.getItemMeta(), locationString, player.getUniqueId().toString());
        // Set Meta
        boundCompass.setItemMeta(meta);
        return boundCompass;
    }

    /**
     * Get the location this compass is bound to
     *
     * @param item Compass item to read from
     **/
    public Location getBoundLocation(ItemStack item) {
        String locationString = nbtManager.getLocationStringData(item);
        return LocationUtil.fromString(locationString);
    }

    public Boolean isOwner(Player player) {
        ItemStack heldItem = ServerVersion.isServerVersionAtOrBelow(ServerVersion.V1_8) ?
            player.getInventory().getItemInHand() : player.getInventory().getItemInMainHand();
        String ownerUUID = nbtManager.getItemOwner(heldItem);
        String playerUUID = player.getUniqueId().toString();
        return ownerUUID.equals(playerUUID);
    }
}
