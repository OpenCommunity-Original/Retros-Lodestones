package com.gitlab.retropronghorn.retroslodestones.handlers;

import com.gitlab.retropronghorn.retroslodestones.RetrosLodestones;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Represents a new MessengerHandler
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
        player.sendActionBar(Component.text(message));
        player.sendActionBar(Component.text(message));
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
        //player.sendTitle(title);
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
