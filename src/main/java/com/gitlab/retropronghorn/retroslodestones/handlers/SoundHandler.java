package com.gitlab.retropronghorn.retroslodestones.handlers;

import com.gitlab.retropronghorn.retroslodestones.RetrosLodestones;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;

/** Represents a new SoundHandler
 * @author RetroPronghorn
 * @author https://gitlab.com/retropronghorn/retros-lodestones
 * @version 1.0-SNAPSHOT
 * @since 1.0
 */
public class SoundHandler {

    /**
     * Play a sound in the world at specific location
     *
     * @param sound Name of the sound
     * @param world The world to play the sound in
     * @param location Location in the world to play the sound
     **/
    public static void playSound(String sound, World world, Location location) {
        if (RetrosLodestones.getPlugin().getConfig().getBoolean("sounds-enabled"))
            world.playSound(location, Sound.valueOf(sound), 1f, 1f);
    }
}
