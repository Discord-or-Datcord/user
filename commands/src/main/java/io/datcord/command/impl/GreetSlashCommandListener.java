package io.datcord.command.impl;

import io.datcord.command.SlashCommandListener;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.internal.interactions.component.ButtonImpl;

public class GreetSlashCommandListener implements SlashCommandListener {

    @Override
    public void onCommandReceived(SlashCommandInteractionEvent commandInteraction) {
        commandInteraction.reply(commandInteraction.getOption("name").getAsString())
                .addActionRow(
                        Button.primary("testId","Test_Text")
                )
                .queue();
    }
}
