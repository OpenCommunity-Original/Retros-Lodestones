package com.gitlab.retropronghorn.retroslodestones.utils;

import com.gitlab.retropronghorn.retroslodestones.RetrosLodestones;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Represents a new experience util
 *
 * @author RetroPronghorn
 * @author https://gitlab.com/retropronghorn/retros-lodestones
 * @version 1.0-SNAPSHOT
 * @since 1.0
 */
public class ExperienceUtil {
    Player player;
    RetrosLodestones instance;

    /**
     * Create a new experience util
     *
     * @param instance Reference to the plugin instance
     * @param player   player to manage experience on
     **/
    public ExperienceUtil(RetrosLodestones instance, Player player) {
        this.instance = instance;
        this.player = player;
    }

    /**
     * Get the total experience a player has
     *
     * @param level level of the player
     * @return returns the total experience by level
     **/
    private static int getTotalExperience(int level) {
        int xp = 0;

        if (level >= 0 && level <= 15) {
            xp = (int) Math.round(Math.pow(level, 2) + 6 * level);
        } else if (level > 15 && level <= 30) {
            xp = (int) Math.round((2.5 * Math.pow(level, 2) - 40.5 * level + 360));
        } else if (level > 30) {
            xp = (int) Math.round(((4.5 * Math.pow(level, 2) - 162.5 * level + 2220)));
        }
        return xp;
    }

    /**
     * Determine if a player has required experience
     *
     * @param requiredExp Required amount of experience
     * @return returns if the player has given required experience
     **/
    public Boolean hasEnoughExperience(Double requiredExp) {
        return (this.getTotalExperience() >= requiredExp);
    }

    /**
     * Deduct experience from player
     *
     * @param amount Amount of experience to deduct
     **/
    public void deductExperience(Double amount) {
        Integer reducedExp = this.getTotalExperience() - ((int) Math.round(amount));
        this.setTotalExperience(reducedExp);
    }

    /**
     * Calculate cost to teleport to a remote location
     *
     * @param to   location we're attempting to teleport to
     * @param from location we're teleporting from
     * @return returns the teleport cost
     **/
    public Double calcTeleportCost(Location to, Location from) {
        if (!to.getWorld().equals(from.getWorld()))
            return RetrosLodestones.getPlugin().getConfig().getDouble("cross-dimension-cost");
        return instance.getConfig().getInt("experience-cost") * (to.distance(from) / 1000);
    }

    /**
     * Get the total experience a player has
     *
     * @return retruns the players total experience
     **/
    private int getTotalExperience() {
        return Math.round(player.getExp() * player.getExpToLevel()) + getTotalExperience(player.getLevel());
    }

    /**
     * Set the total experience a player has
     *
     * @param amount amount of experience to set on the player
     **/
    private void setTotalExperience(int amount) {
        int level = 0;
        int xp = 0;
        float a = 0;
        float b = 0;
        float c = -amount;

        if (amount > getTotalExperience(0) && amount <= getTotalExperience(15)) {
            a = 1;
            b = 6;
        } else if (amount > getTotalExperience(15) && amount <= getTotalExperience(30)) {
            a = 2.5f;
            b = -40.5f;
            c += 360;
        } else if (amount > getTotalExperience(30)) {
            a = 4.5f;
            b = -162.5f;
            c += 2220;
        }
        level = (int) Math.floor((-b + Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a));
        xp = amount - getTotalExperience(level);
        player.setLevel(level);
        player.setExp(0);
        player.giveExp(xp);
    }
}
