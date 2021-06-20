package com.gitlab.retropronghorn.retroslodestones.handlers;

import java.util.ArrayList;
import java.util.List;

import com.gitlab.retropronghorn.retroslodestones.RetrosLodestones;
import com.gitlab.retropronghorn.retroslodestones.utils.ServerVersion;
import com.gitlab.retropronghorn.retroslodestones.utils.StringColorParser;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class NBTManager {
    RetrosLodestones instance;
    // Identifier keys
    public static final String KEY_LOCATION = "location";
    public static final String KEY_OWNER = "OWNER";
    // Namespaced Keys (Objects to support legacy versions)
    public Object locationKeyIdentifier;
    public Object ownerKeyIdentifier;

    public NBTManager(RetrosLodestones instance) {
        this.instance = instance;
        if (ServerVersion.isServerVersionAtLeast(ServerVersion.V1_13)) {
            this.locationKeyIdentifier = new NamespacedKey(instance, KEY_LOCATION);
            this.ownerKeyIdentifier = new NamespacedKey(instance, KEY_OWNER);
        }
    }
    
    /**
     * Get location string
     *
     * @param item Compass item
     * @return returns location as string
     **/
    public String getLocationStringData(ItemStack item) {
        if (ServerVersion.isServerVersionAtLeast(ServerVersion.V1_13)) {
            String locationString = item.getItemMeta()
                    .getPersistentDataContainer()
                    .get((NamespacedKey) locationKeyIdentifier, PersistentDataType.STRING);

            return locationString;
        } else {
            return StringColorParser.getLocationString(instance, item);
        }
    }

    /**
     * Get owner of compass
     *
     * @param item Compass item
     * @return returns owner uuid string
     **/
    public String getItemOwner(ItemStack item) {
        if (ServerVersion.isServerVersionAtLeast(ServerVersion.V1_13)) {
            String locationString = item.getItemMeta()
                    .getPersistentDataContainer()
                    .get((NamespacedKey) ownerKeyIdentifier, PersistentDataType.STRING);

            return locationString;
        } else {
            return StringColorParser.getUUID(instance, item);
        }
    }

    /**
     * Apply binding and display metadata
     *
     * @param meta Metadata to bind
     * @param locationString Location to add to metadata
     * @param ownerUUID Owner of the compass
     **/
    public ItemMeta setCompassMeta(ItemMeta meta, String locationString, String ownerUUID) {
        ItemMeta newMeta = meta.clone();
        if (ServerVersion.isServerVersionBelow(ServerVersion.V1_16))
            newMeta.setDisplayName(instance.getLanguageConfig().getString("compass-name"));

        // Set lore on item
        List<String> lore = new ArrayList<>();
        lore.add(instance.getLanguageConfig().getString("compass-lore"));
        if(ServerVersion.isServerVersionBelow(ServerVersion.V1_13))
            lore.add(StringColorParser.uuidToColor(ownerUUID) + instance.getLanguageConfig().getString("compass-bound") + locationString);
        else
            lore.add(locationString);
        newMeta.setLore(lore);

        // Add fake enchantment for older versions
        if (ServerVersion.isServerVersionBelow(ServerVersion.V1_16)) {
            newMeta.addEnchant(Enchantment.DURABILITY, 1, true);
            newMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        if (ServerVersion.isServerVersionAtLeast(ServerVersion.V1_13)) {
            // Set persistent data
            newMeta.getPersistentDataContainer().set(
                    (NamespacedKey) locationKeyIdentifier,
                    PersistentDataType.STRING,
                    locationString);

            newMeta.getPersistentDataContainer().set(
                    (NamespacedKey) ownerKeyIdentifier,
                    PersistentDataType.STRING,
                    ownerUUID);
        }
        return newMeta;
    }
    
}