package com.gitlab.retropronghorn.retroslodestones.utils;

import com.gitlab.retropronghorn.retroslodestones.RetrosLodestones;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class LodestoneUtil {
    public final static String LODESTONE_BLOCK = RetrosLodestones.getPlugin().getConfig().getString("lodestone-item");

    /**
     * Check that a lodestone exists at location
     *
     * @param location Location to expect lodestone
     * @return Retruns wether or not the lodestone exists at the given location
     **/
    public static Boolean isMissing(Location location) {
        Block block = location.getBlock();
        // TODO: Add identifiers to block to prevent placing different lodestone in the same location counting
        return !block.getType().toString().equals(LODESTONE_BLOCK);
    }
}
