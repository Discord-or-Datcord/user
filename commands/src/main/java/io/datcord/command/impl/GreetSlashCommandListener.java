package io.datcord.command.impl;

import io.datcord.command.SlashCommandListener;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class GreetSlashCommandListener implements SlashCommandListener {

    @Override
    public void onCommandReceived(SlashCommandInteractionEvent commandInteraction) {
        commandInteraction.reply(commandInteraction.getOption("name").getAsString()).queue();
    }
}
