package com.gitlab.retropronghorn.retroslodestones.utils;

import com.gitlab.retropronghorn.retroslodestones.RetrosLodestones;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/** Represents a new logging util
 * @author RetroPronghorn
 * @author https://gitlab.com/retropronghorn/retros-lodestones
 * @version 1.0-SNAPSHOT
 * @since 1.0
 */
public class LoggingUtil {
    /**
     * Wrap a string in the plugin formatter
     *
     * @param message message to wrap
     **/
    private static void wrap(String message) {
        Bukkit.getConsoleSender().sendMessage("["+ChatColor.LIGHT_PURPLE+"Retro's Loadstones"+ChatColor.RESET+"]: "+message);
    }

    /**
     * Log to the info level
     *
     * @param message message to log
     **/
    public static void info(String message) {
        if (RetrosLodestones.getPlugin().getConfig().getBoolean("verbose-logging"))
            wrap(ChatColor.WHITE + message);
    }

    /**
     * Log to the success level
     *
     * @param message message to log
     **/
    public static void success(String message) {
        if (RetrosLodestones.getPlugin().getConfig().getBoolean("verbose-logging"))
            wrap(ChatColor.GREEN + message);
    }

    /**
     * Log to the warning level
     *
     * @param message message to log
     **/
    public static void warning(String message) {
        wrap(ChatColor.YELLOW + message);
    }

    /**
     * Log to the error level
     *
     * @param message message to log
     **/
    public static void error(String message) {
        wrap(ChatColor.RED + message);
    }
}
