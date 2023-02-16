package com.gitlab.retropronghorn.retroslodestones.handlers;

import com.gitlab.retropronghorn.retroslodestones.RetrosLodestones;
import com.gitlab.retropronghorn.retroslodestones.items.Compass;
import com.gitlab.retropronghorn.retroslodestones.utils.LocaleAPI;
import com.gitlab.retropronghorn.retroslodestones.utils.LocationUtil;
import com.gitlab.retropronghorn.retroslodestones.utils.LoggingUtil;
import com.gitlab.retropronghorn.retroslodestones.utils.TeleportUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

/**
 * Represents a new CompassHandler
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
        String action = event.getAction().name();
        if (action.equals("RIGHT_CLICK_BLOCK"))
            handleRightClickEvent(event);
        if (action.equals("LEFT_CLICK_BLOCK"))
            handleLeftClickEvent(event);
    }

    /**
     * Handles right clicks if on the lodestone-item
     *
     * @param event Event to handle
     **/
    private void handleRightClickEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        Location location = block.getLocation();
        World world = block.getWorld();
        ItemStack item = event.getItem();
        if (Objects.requireNonNull(block)
                .getType()
                .toString()
                .equals(config.getString("lodestone-item"))) {
            // Cancel potential events on custom lodestones
            if (!block
                    .getType()
                    .toString()
                    .equals(config.getString("lodestone-default"))) {
                event.setCancelled(true);
                LoggingUtil.warning(player.displayName() + " caught custom lodestone block clicked. Cancelling events.");
            }
            if (compass.isBound(item)) { // Compass is already bound
                MessengerHandler.sendActionbarMessage(player,
                        LocaleAPI.getMessage(player,"already-bound"));
                SoundHandler
                        .playSound(
                                instance.getSoundsConfig().getString("already-bound"),
                                world,
                                location);
                LoggingUtil.info(player.displayName() + " failed to bind a compass (already-bound)");
            } else { // Bind Compass
                SoundHandler
                        .playSound(
                                instance.getSoundsConfig().getString("bind-successful"),
                                world,
                                location);
                ParticleHandler
                        .spawnParticle(
                                instance.getParticlesConfig().getString("bind-successful"),
                                world,
                                block.getLocation(),
                                15);
                compass.bindCompass(player, item, location);
                LoggingUtil.info(player.displayName() + " bound a compass at " + location);
            }
        }
    }

    /**
     * Handles left clicks if on the respawn-anchor-item
     *
     * @param event Event to handle
     **/
    private void handleLeftClickEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        Location location = block.getLocation();
        World world = block.getWorld();
        ItemStack item = event.getItem();
        if (Objects.requireNonNull(block)
                .getType()
                .toString()
                .equals(config.getString("respawn-anchor-item"))) {
            if (compass.isBound(item)) { // Handle Teleport
                if ((instance.getConfig().getBoolean("owner-only-teleport") &&
                        compass.isOwner(player)) || !instance.getConfig().getBoolean("owner-only-teleport")) {
                    // Make sure we're not teleporting from the bound lodestone to the bound lodestone
                    if (compass.isTeleportingPointless(location, player)) {
                        sendMessageSoundParticle(player, location, world, "pointless-teleport", "teleport-failed", "pointless-teleport");
                        LoggingUtil.info(player.displayName() + " attempted a pointless teleport (pointless-teleport)");
                    } else {
                        Boolean missingLodestone = compass.lodestoneMissing(player);
                        if (missingLodestone) {
                            SoundHandler.playSound(
                                    instance.getSoundsConfig().getString("obstructed-lodestone"),
                                    world,
                                    location);
                            ParticleHandler.spawnParticle(
                                    instance.getParticlesConfig().getString("teleport-failed"),
                                    world,
                                    location);
                            MessengerHandler.sendActionbarMessage(player, LocaleAPI.getMessage(player,"obstructed-lodestone"));
                            return;
                        }

                        ItemStack compassItem = player.getInventory().getItemInMainHand();
                        String locationString = nbtManager.getLocationStringData(compassItem);
                        Boolean freeSpace = TeleportUtil.freeSpaceAbove(Objects.requireNonNull(LocationUtil.fromString(locationString)));
                        if (!freeSpace) {
                            SoundHandler.playSound(
                                    instance.getSoundsConfig().getString("obstructed-lodestone"),
                                    world,
                                    location);
                            ParticleHandler.spawnParticle(
                                    instance.getParticlesConfig().getString("teleport-failed"),
                                    world,
                                    location);
                            MessengerHandler.sendActionbarMessage(player, LocaleAPI.getMessage(player,"obstructed-lodestone"));
                            return;
                        }

                        Boolean teleport = compass.doTeleport(player);
                        if (!teleport) {
                            sendMessageSoundParticle(player, location, world, "insufficient-experience", "teleport-failed", "insufficient-experience");
                            LoggingUtil.info(player.displayName() + " failed to teleport (insufficient-experience)");
                            return;
                        }

                        if (instance.getConfig().getBoolean("teleportation-sickness"))
                            applyTeleportSickness(instance, player);

                        sendMessageSoundParticle(player, location, world, "teleport-successful", "teleport-success", "teleport-successful");
                        ParticleHandler.spawnParticle(
                                instance.getParticlesConfig().getString("teleport-success-player"),
                                player.getWorld(),
                                player.getLocation(),
                                instance.getParticlesConfig().getInt("teleport-succcess-player-count"));

                        LoggingUtil.info(player.displayName() + " teleported successfully " + player.getLocation());

                    }
                } else {
                    sendMessageSoundParticle(player, location, world, "not-owner", "teleport-failed", "not-owner");
                    LoggingUtil.info(player.displayName() + " attempted to teleport using un-owned compass");
                }
            } else { // Compass is not bound
                sendMessageSoundParticle(player, location, world, "not-bound", "teleport-failed", "not-bound");
                LoggingUtil.info(player.displayName() + " attempted to teleport using unbound compass");
            }
        }
    }

    /**
     * Handles playing sounds, sending messages and spawning particles.
     **/
    private void sendMessageSoundParticle(Player player, Location location, World world, String message, String particle, String sound) {
        MessengerHandler.sendActionbarMessage(
                player,
                LocaleAPI.getMessage(player, message));
        ParticleHandler.spawnParticle(
                instance.getParticlesConfig().getString(particle),
                Objects.requireNonNull(world
                ), location, 5);
        SoundHandler.playSound(
                instance.getSoundsConfig().getString(sound),
                world,
                location);
    }

    /**
     * Applies teleport sickness to a player
     **/
    private void applyTeleportSickness(RetrosLodestones instance, Player player) {
        player.addPotionEffect(
                new PotionEffect(
                        PotionEffectType.WEAKNESS,
                        instance.getConfig().getInt("teleportation-sickness-duration"),
                        1));
        player.addPotionEffect(
                new PotionEffect(
                        PotionEffectType.CONFUSION,
                        instance.getConfig().getInt("teleportation-sickness-duration"),
                        4));
        player.addPotionEffect(
                new PotionEffect(
                        PotionEffectType.LEVITATION,
                        instance.getConfig().getInt("teleport-delay"),
                        1));
        player.addPotionEffect(
                new PotionEffect(
                        PotionEffectType.BLINDNESS,
                        instance.getConfig().getInt("teleportation-sickness-duration"),
                        1));
    }

    /**
     * Handle compass functionality when sneak clickcing
     **/
    public void handleTrack(Player player) {
        ItemStack compassItem = player.getInventory().getItemInMainHand();
        Location boundLocation = compass.getBoundLocation(compassItem);
        player.setCompassTarget(boundLocation);
        MessengerHandler
                .sendActionbarMessage(
                        player,
                        LocaleAPI.getMessage(player,"locked-on") +
                                " " +
                                Math.floor(player.getLocation().distance(boundLocation)) +
                                " blocks away.");
    }
}
