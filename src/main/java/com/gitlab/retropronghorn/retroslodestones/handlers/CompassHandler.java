package com.gitlab.retropronghorn.retroslodestones.handlers;

import com.gitlab.retropronghorn.retroslodestones.RetrosLodestones;
import com.gitlab.retropronghorn.retroslodestones.items.Compass;
import com.gitlab.retropronghorn.retroslodestones.utils.LocationUtil;
import com.gitlab.retropronghorn.retroslodestones.utils.LoggingUtil;
import com.gitlab.retropronghorn.retroslodestones.utils.ServerVersion;
import com.gitlab.retropronghorn.retroslodestones.utils.TeleportUtil;
import com.gitlab.retropronghorn.retroslodestones.utils.VersionUtil;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/** Represents a new CompassHandler
 * @author RetroPronghorn
 * @author https://gitlab.com/retropronghorn/retros-lodestones
 * @version 1.0-SNAPSHOT
 * @since 1.0
 */
public class CompassHandler {
    private static final FileConfiguration config = RetrosLodestones.getPlugin().getConfig();
    NBTManager nbtManager;
    RetrosLodestones instance;
    MessengerHandler messengerHandler;
    ParticleHandler particleHandler;
    SoundHandler soundHandler;
    Compass compass;

    /**
     * Create a new compass handler
     *
     * @param instance reference to the plugin instance
     **/
    public CompassHandler(RetrosLodestones instance) {
        this.instance = instance;
        this.nbtManager = new NBTManager(instance);
        this.compass = new Compass(instance);
        this.messengerHandler = new MessengerHandler();
        this.particleHandler = new ParticleHandler();
        this.soundHandler = new SoundHandler();
    }

    /**
     * Handles player click and dispatches events for each click type
     *
     * @param event Event to handle
     **/
    public void handlePlayerInteraction(PlayerInteractEvent event) {
        if (event.getAction().name().equals("RIGHT_CLICK_BLOCK"))
            handleRightClickEvent(event);
        if (event.getAction().name().equals("LEFT_CLICK_BLOCK"))
            handleLeftClickEvent(event);
    }

    /**
     * Handles right clicks if on the lodestone-item
     *
     * @param event Event to handle
     **/
    private void handleRightClickEvent(PlayerInteractEvent event) {
        if (event.getClickedBlock()
                .getType()
                .toString()
                .equals(config.getString("lodestone-item"))) {
            // Cancel potential events on custom lodestones
            if (!event.getClickedBlock()
                    .getType()
                    .toString()
                    .equals(config.getString("lodestone-default"))) {
                event.setCancelled(true);
                LoggingUtil.warning(event.getPlayer().getDisplayName() + " caught custom lodestone block clicked. Cancelling events.");
            }
            Location location = event.getClickedBlock().getLocation();
            if (compass.isBound(event.getItem())) { // Compass is already bound
                MessengerHandler.sendActionbarMessage(event.getPlayer(),
                        instance.getLanguageConfig().getString("already-bound"));
                SoundHandler
                        .playSound(
                        instance.getSoundsConfig().getString("already-bound"),
                        event.getClickedBlock().getWorld(),
                        event.getClickedBlock().getLocation());
                LoggingUtil.info(event.getPlayer().getDisplayName() + " failed to bind a compass (already-bound)");
            } else { // Bind Compass
                SoundHandler
                        .playSound(
                        instance.getSoundsConfig().getString("bind-successful"),
                        event.getClickedBlock().getWorld(),
                        event.getClickedBlock().getLocation());
                ParticleHandler
                        .spawnParticle(
                        instance.getParticlesConfig().getString("bind-successful"),
                        event.getClickedBlock().getWorld(),
                        event.getClickedBlock().getLocation(),
                        15);
                compass.bindCompass(event.getPlayer(), event.getItem(), location);
                LoggingUtil.info(event.getPlayer().getDisplayName() + " bound a compass at " + location);
            }
        }
    }

    /**
     * Handles left clicks if on the respawn-anchor-item
     *
     * @param event Event to handle
     **/
    private void handleLeftClickEvent(PlayerInteractEvent event) {
        if (event.getClickedBlock()
                .getType()
                .toString()
                .equals(config.getString("respawn-anchor-item"))) {
            if (compass.isBound(event.getItem())) { // Handle Teleport
                if ((instance.getConfig().getBoolean("owner-only-teleport") &&
                        compass.isOwner(event.getPlayer())) || !instance.getConfig().getBoolean("owner-only-teleport")) {
                    // Make sure we're not teleporting from the bound lodestone to the bound lodestone
                    if (compass.isTeleportingPointless(event.getClickedBlock().getLocation(), event.getPlayer())) {
                        sendMessageSoundParticle(event, "pointless-teleport", "teleport-failed", "pointless-teleport");
                        LoggingUtil.info(event.getPlayer().getDisplayName() + " attempted a pointless teleport (pointless-teleport)");
                    } else {
                        Boolean missingLodestone = compass.lodestoneMissing(event.getPlayer());
                        if (missingLodestone) {
                            SoundHandler.playSound(
                                instance.getSoundsConfig().getString("obstructed-lodestone"),
                                event.getClickedBlock().getLocation().getWorld(),
                                event.getClickedBlock().getLocation());
                            ParticleHandler.spawnParticle(
                                instance.getParticlesConfig().getString("teleport-failed"),
                                event.getClickedBlock().getLocation().getWorld(),
                                event.getClickedBlock().getLocation());
                            MessengerHandler.sendActionbarMessage(event.getPlayer(), instance.getLanguageConfig().getString("obstructed-lodestone"));
                            return;
                        }

                        ItemStack compassItem = ServerVersion.isServerVersionAtOrBelow(ServerVersion.V1_8) ?
                            event.getPlayer().getInventory().getItemInHand() : event.getPlayer().getInventory().getItemInMainHand();
                        String locationString = nbtManager.getLocationStringData(compassItem);
                        Boolean freeSpace = TeleportUtil.freeSpaceAbove(LocationUtil.fromString(locationString));
                        if (!freeSpace) {
                            SoundHandler.playSound(
                                instance.getSoundsConfig().getString("obstructed-lodestone"),
                                event.getClickedBlock().getLocation().getWorld(),
                                event.getClickedBlock().getLocation());
                            ParticleHandler.spawnParticle(
                                instance.getParticlesConfig().getString("teleport-failed"),
                                event.getClickedBlock().getLocation().getWorld(),
                                event.getClickedBlock().getLocation());
                            MessengerHandler.sendActionbarMessage(event.getPlayer(), instance.getLanguageConfig().getString("obstructed-lodestone"));
                            return;
                        }

                        Boolean teleport = compass.doTeleport(event.getPlayer());
                        if (!teleport) {
                            sendMessageSoundParticle(event, "insufficient-experience", "teleport-failed", "insufficient-experience");
                            LoggingUtil.info(event.getPlayer().getDisplayName() + " failed to teleport (insufficient-experience)");
                            return;
                        }

                        if (instance.getConfig().getBoolean("teleportation-sickness"))
                        applyTeleportSickness(instance, event);

                        sendMessageSoundParticle(event, "teleport-successful", "teleport-success", "teleport-successful");
                        ParticleHandler.spawnParticle(
                            instance.getParticlesConfig().getString("teleport-success-player"),
                            event.getPlayer().getWorld(),
                            event.getPlayer().getLocation(),
                            instance.getParticlesConfig().getInt("teleport-succcess-player-count"));

                        LoggingUtil.info(event.getPlayer().getDisplayName() + " teleported successfully " + event.getPlayer().getLocation());

                    }
                } else {
                    sendMessageSoundParticle(event, "not-owner", "teleport-failed", "not-owner");
                    LoggingUtil.info(event.getPlayer().getDisplayName() + " attempted to teleport using un-owned compass");
                }
            } else { // Compass is not bound
                sendMessageSoundParticle(event, "not-bound", "teleport-failed", "not-bound");
                LoggingUtil.info(event.getPlayer().getDisplayName() + " attempted to teleport using unbound compass");
            }
        }
    }

    /**
     * Handles playing sounds, sending messages and spawning particles.
     *
     * @param event Event to handle
     * @param message Message to send
     * @param sound Sound to play
     **/
    private void sendMessageSoundParticle(PlayerInteractEvent event, String message, String particle, String sound) {
        MessengerHandler.sendActionbarMessage(
                event.getPlayer(),
                instance.getLanguageConfig().getString(message));
        ParticleHandler.spawnParticle(
            instance.getParticlesConfig().getString(particle),
            event.getClickedBlock().getWorld(),
            event.getClickedBlock().getLocation(),
            5);
        SoundHandler.playSound(
            instance.getSoundsConfig().getString(sound),
            event.getClickedBlock().getWorld(),
            event.getClickedBlock().getLocation());
    }

    /**
     * Applies teleport sickness to a player
     *
     * @param instance RetrosLodestone plugin instance
     * @param event Event to act on
     **/
    private void applyTeleportSickness(RetrosLodestones instance, PlayerInteractEvent event) {
        event.getPlayer().addPotionEffect(
                new PotionEffect(
                        PotionEffectType.WEAKNESS,
                        instance.getConfig().getInt("teleportation-sickness-duration"),
                        1));
        event.getPlayer().addPotionEffect(
                new PotionEffect(
                        PotionEffectType.CONFUSION,
                        instance.getConfig().getInt("teleportation-sickness-duration"),
                        4));
        event.getPlayer().addPotionEffect(
                new PotionEffect(
                        PotionEffectType.LEVITATION,
                        instance.getConfig().getInt("teleport-delay"),
                        1));
    }
    
    /**
     * Handle compass functionality when sneak clickcing
     *
     * @param event Event to act on
     **/
    public void handleTrack(PlayerInteractEvent event) {
        if (!VersionUtil.isAtOrAbove(VersionUtil.TARGET_VERSION)) {
            ItemStack compassItem = ServerVersion.isServerVersionAtOrBelow(ServerVersion.V1_8) ?
                event.getPlayer().getInventory().getItemInHand() : event.getPlayer().getInventory().getItemInMainHand();
            Location boundLocation = compass.getBoundLocation(compassItem);
            event.getPlayer().setCompassTarget(boundLocation);
            MessengerHandler
                .sendActionbarMessage(
                    event.getPlayer(),
                    instance.getLanguageConfig().getString("locked-on") +
                            " " +
                            Math.floor(event.getPlayer().getLocation().distance(boundLocation)) +
                            " blocks away.");
        }
    }
}
