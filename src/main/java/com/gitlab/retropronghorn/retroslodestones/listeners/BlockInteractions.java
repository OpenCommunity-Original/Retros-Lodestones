package com.gitlab.retropronghorn.retroslodestones.listeners;

import com.gitlab.retropronghorn.retroslodestones.RetrosLodestones;
import com.gitlab.retropronghorn.retroslodestones.handlers.CompassHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Represents a new BlockInteractions listener
 */
public class BlockInteractions implements Listener {
    RetrosLodestones instance;
    CompassHandler compassHandler;

    /**
     * Create a new compass item
     *
     * @param instance Reference to the plugin instance
     **/
    public BlockInteractions(RetrosLodestones instance) {
        this.instance = instance;
        compassHandler = new CompassHandler(instance);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (player.hasPermission("retroslodestones.use")) {
            if (heldItem.getType().toString().equals(instance.getConfig().getString("compass-item"))) {
                if (event.getAction().name().equals("RIGHT_CLICK_AIR")) {
                    compassHandler.handleTrack(player);
                } else {
                    compassHandler.handlePlayerInteraction(event);
                }
            }
        }
    }
}
