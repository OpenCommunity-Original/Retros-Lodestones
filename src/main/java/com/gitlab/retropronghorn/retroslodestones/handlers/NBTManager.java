package com.gitlab.retropronghorn.retroslodestones.handlers;

import com.gitlab.retropronghorn.retroslodestones.RetrosLodestones;
import com.gitlab.retropronghorn.retroslodestones.utils.StringColorParser;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NBTManager {
    // Identifier keys
    public static final String KEY_LOCATION = "location";
    public static final String KEY_OWNER = "OWNER";
    // Namespaced Keys (Objects to support legacy versions)
    public Object locationKeyIdentifier;
    public Object ownerKeyIdentifier;
    RetrosLodestones instance;

    public NBTManager(RetrosLodestones instance) {
        this.instance = instance;
        this.locationKeyIdentifier = new NamespacedKey(instance, KEY_LOCATION);
        this.ownerKeyIdentifier = new NamespacedKey(instance, KEY_OWNER);
    }

    /**
     * Get location string
     *
     * @param item Compass item
     * @return returns location as string
     **/
    public String getLocationStringData(ItemStack item) {
        String locationString = item.getItemMeta()
                .getPersistentDataContainer()
                .get((NamespacedKey) locationKeyIdentifier, PersistentDataType.STRING);

        return locationString;
    }

    /**
     * Get owner of compass
     *
     * @param item Compass item
     * @return returns owner uuid string
     **/
    public String getItemOwner(ItemStack item) {
        String locationString = item.getItemMeta()
                .getPersistentDataContainer()
                .get((NamespacedKey) ownerKeyIdentifier, PersistentDataType.STRING);

        return locationString;
    }

    /**
     * Apply binding and display metadata
     *
     * @param meta           Metadata to bind
     * @param locationString Location to add to metadata
     * @param ownerUUID      Owner of the compass
     **/
    public ItemMeta setCompassMeta(ItemMeta meta, String locationString, String ownerUUID) {
        ItemMeta newMeta = meta.clone();

        newMeta.setDisplayName(instance.getLanguageConfig().getString("compass-name"));

        // Set lore on item
        List<String> lore = new ArrayList<>();
        lore.add(instance.getLanguageConfig().getString("compass-lore"));
        lore.add(StringColorParser.uuidToColor(ownerUUID) + instance.getLanguageConfig().getString("compass-bound") + locationString);
        newMeta.setLore(lore);

        newMeta.getPersistentDataContainer().set(
                (NamespacedKey) locationKeyIdentifier,
                PersistentDataType.STRING,
                locationString);

        newMeta.getPersistentDataContainer().set(
                (NamespacedKey) ownerKeyIdentifier,
                PersistentDataType.STRING,
                ownerUUID);
        return newMeta;
    }

}