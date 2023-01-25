package com.gitlab.retropronghorn.retroslodestones.handlers;

import com.gitlab.retropronghorn.retroslodestones.RetrosLodestones;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

/**
 * Represents a new ParticleHandler
 */
public class ParticleHandler {

    /**
     * Spawn a new particle in the world
     *
     * @param type     Particle name
     * @param world    World to spawn the particle
     * @param location Location to spawn the particle
     **/
    public static void spawnParticle(String type, World world, Location location) {
        if (RetrosLodestones.getPlugin().getConfig().getBoolean("particles-enabled"))
            world.spawnParticle(Particle.valueOf(type), location.getX() + 0.5, location.getY() + 1, location.getZ() + 0.5, 1);
    }

    /**
     * Spawn a new particle in the world
     *
     * @param type     Particle name
     * @param world    World to spawn the particle(s)
     * @param location Location to spawn the particle(s)
     * @param count    Amount of particles to spawn
     **/
    public static void spawnParticle(String type, World world, Location location, Integer count) {
        if (RetrosLodestones.getPlugin().getConfig().getBoolean("particles-enabled"))
            world.spawnParticle(Particle.valueOf(type), location.getX() + 0.5, location.getY() + 1, location.getZ() + 0.5, count);
    }
}
