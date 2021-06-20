package com.gitlab.retropronghorn.retroslodestones.commands;

import com.gitlab.retropronghorn.retroslodestones.RetrosLodestones;
import com.gitlab.retropronghorn.retroslodestones.handlers.MessengerHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/** Represents a new InfoCommand
 * @author RetroPronghorn
 * @author https://gitlab.com/retropronghorn/retros-lodestones
 * @version 1.0-SNAPSHOT
 * @since 1.0
 */
public class InfoCommand implements CommandExecutor {
    private RetrosLodestones instance;

    /**
     * Create a new InfoCommand
     *
     * @param instance Reference to the plugin instance
     **/
    public InfoCommand(RetrosLodestones instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player senderPlayer = Bukkit.getPlayer(sender.getName());
        MessengerHandler.sendActionbarMessage(
                senderPlayer,
                ChatColor.GREEN +
                        "Retro's Lodestones v" +
                        instance.getDescription().getVersion() +
                        " by " +
                        instance.getDescription().getAuthors());
        MessengerHandler.sendChatMessage(
                senderPlayer,
                ChatColor.GREEN +
                "Retro's Lodestones v" +
                        instance.getDescription().getVersion() +
                        " by " +
                        instance.getDescription().getAuthors());
        return false;

    }
}