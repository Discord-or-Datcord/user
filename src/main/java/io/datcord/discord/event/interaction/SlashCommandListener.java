package io.datcord.discord.event.interaction;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlashCommandListener extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SlashCommandListener.class);
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "test" -> {
                //TODO replace with actual command handler
            }
            default -> {
                return;
            }
        }
    }
}
