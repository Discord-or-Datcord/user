package io.datcord.command;

import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

/**
 * Interface for handling slash command interactions.
 */
public interface SlashCommandListener {

    /**
     * Handles the received slash command interaction.
     *
     * @param commandInteraction The slash command interaction to handle.
     */
    void onCommandReceived(SlashCommandInteraction commandInteraction);
}

