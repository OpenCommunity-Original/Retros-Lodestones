package com.gitlab.retropronghorn.retroslodestones.handlers;

import com.gitlab.retropronghorn.retroslodestones.RetrosLodestones;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Represents a new MessengerHandler
 *
 * @author RetroPronghorn
 * @author https://gitlab.com/retropronghorn/retros-lodestones
 * @version 1.0-SNAPSHOT
 * @since 1.0
 */
public class MessengerHandler {

    static Plugin plugin = RetrosLodestones.getInstance();

    /**
     * Dispatch a new actionbar message
     *
     * @param player  User to send actionbar message to
     * @param message Message to display on the actionbar
     **/
    public static void sendActionbarMessage(Player player, String message) {
        BukkitAudiences.create(plugin).player(player).sendActionBar(Component.text(message));
    }

    /**
     * Dispatch a new chat message
     *
     * @param player  User to send chat message to
     * @param message Message to display in the chat
     **/
    public static void sendChatMessage(Player player, String message) {
        player.sendMessage(message);
    }

    /**
     * Dispatch a new title message
     *
     * @param player User to send title to
     * @param title  Title to send
     */
    public static void sendTitle(Player player, String title) {
        //BukkitAudiences.create(plugin).player(player).sendTitle(title);
    }

    /**
     * Dispatch a new title message
     *
     * @param player   User to send title to
     * @param title    Title to send
     * @param subtitle Subtitle to send
     */
    public static void sendTitle(Player player, String title, String subtitle) {
        //player.sendTitle(new Title(title, subtitle));
    }
}
