package io.datcord.command.ticket;

import io.datcord.event.EventModuleInitializer;
import io.datcord.event.interaction.EntitySelectInteractionEventListener;
import io.datcord.event.interaction.registry.EntitySelectInteractionEventListenerRegistry;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
