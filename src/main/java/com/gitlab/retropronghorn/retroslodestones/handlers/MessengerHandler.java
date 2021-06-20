package com.gitlab.retropronghorn.retroslodestones.handlers;

import com.destroystokyo.paper.Title;
import com.gitlab.retropronghorn.retroslodestones.utils.ServerVersion;
import com.gitlab.retropronghorn.retroslodestones.utils.VersionUtil;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

/** Represents a new MessengerHandler
 * @author RetroPronghorn
 * @author https://gitlab.com/retropronghorn/retros-lodestones
 * @version 1.0-SNAPSHOT
 * @since 1.0
 */
public class MessengerHandler {
    /**
     * Dispatch a new actionbar message
     *
     * @param player User to send actionbar message to
     * @param message Message to display on the actionbar
     **/
    public static void sendActionbarMessage(Player player, String message) {
        if (VersionUtil.isAtOrAbove(ServerVersion.V1_15)) {
            player.sendActionBar(message);
        } else if (VersionUtil.isAtOrAbove(ServerVersion.V1_12)) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
        } else {
            player.sendMessage(message);
        }
    }

    /**
     * Dispatch a new chat message
     *
     * @param player User to send chat message to
     * @param message Message to display in the chat
     **/
    public static void sendChatMessage(Player player, String message) { player.sendMessage(message); }

    /** Dispatch a new title message
     * @param player User to send title to
     * @param title Title to send
     */
    public static void sendTitle(Player player, String title) {
        player.sendTitle(new Title(title));
    }

    /** Dispatch a new title message
     * @param player User to send title to
     * @param title Title to send
     * @param subtitle Subtitle to send
     */
    public static void sendTitle(Player player, String title, String subtitle) {
        player.sendTitle(new Title(title, subtitle));
    }
}
