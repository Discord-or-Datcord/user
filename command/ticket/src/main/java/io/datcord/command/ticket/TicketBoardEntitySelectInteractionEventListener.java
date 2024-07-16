package io.datcord.command.ticket;

import io.datcord.event.EventModuleInitializer;
import io.datcord.event.interaction.EntitySelectInteractionEventListener;
import io.datcord.event.interaction.registry.EntitySelectInteractionEventListenerRegistry;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible for handling entity selection interaction events within the ticket board.
 * The class implements both the EventModuleInitializer and EntitySelectInteractionEventListener interfaces.
 * It essentially logs when an entity selection interaction event happens, receives the message from the event,
 * and then modifies the original embeds of the hook from the event with a new EmbedBuilder instance.
 * The new embed built by the EmbedBuilder has a title of "Ticket Board Builder" and asks, "What should the board say?".
 */
public class TicketBoardEntitySelectInteractionEventListener implements EventModuleInitializer, EntitySelectInteractionEventListener {


    private static final Logger logger = LoggerFactory.getLogger(TicketBoardEntitySelectInteractionEventListener.class);

    @Override
    public void init() {
        EntitySelectInteractionEventListenerRegistry.register("ticket_board_channel", this);
    }

    @Override
    public void onEntitySelectInteraction(EntitySelectInteractionEvent event) {
        logger.info("Received EntitySelectInteractionEvent: {}", event);
        logger.debug("Received message: {}", event.getMessage());
        event.deferEdit()
                .flatMap(hook -> hook.editOriginalEmbeds
                        (
                                new EmbedBuilder()
                                        .setTitle("Ticket Board Builder")
                                        .setDescription("What should the board say?")
                                        .build()
                        )
                )
                .queue();
    }
}
