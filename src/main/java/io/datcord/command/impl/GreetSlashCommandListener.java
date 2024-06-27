package io.datcord.command.impl;

import com.google.common.eventbus.Subscribe;
import io.datcord.command.SlashCommandListener;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

public class GreetSlashCommandListener implements SlashCommandListener {

    @Override
    public void onCommandReceived(SlashCommandInteraction commandInteraction) {
        commandInteraction.reply(commandInteraction.getOption("name").getAsString()).queue();
    }
}
