package io.datcord.command.ticket;

import io.datcord.command.CommandModuleInitializer;
import io.datcord.command.SlashCommandListener;
import io.datcord.command.SlashCommandListenerRegistry;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu;

import java.time.Duration;

public class TicketSlashCommandListener implements SlashCommandListener, CommandModuleInitializer {

    /**
     * The reaction to receiving the 'ticket' command
     *
     * TODO: This looks like doodoo presently but it does function as expected
     *
     * @param event The slash command interaction to handle.
     */
    @Override
    public void onCommandReceived(SlashCommandInteractionEvent event) {
        event.replyEmbeds( //Build an ember to contain the board building wizard
                        new EmbedBuilder()
                                .setTitle("Ticket Board Builder")
                                .setDescription("What channel should the board be created?")
                                .build()
                )
                .addActionRow( //create a dropdown selection menu of all channels TODO: Change this to specific channels
                        EntitySelectMenu.create("ticket_board_channel", EntitySelectMenu.SelectTarget.CHANNEL)
                                .build()
                )
                .setEphemeral(true) //Only visible to command sender
                .delay(Duration.ofSeconds(10))//This does nothing. It can be used to auto-delete
                .queue();
    }

    @Override
    public void initCommand() {
        SlashCommandListenerRegistry.register("ticket", this);
    }
}
