package io.datcord.command.ticket;

import com.google.common.eventbus.Subscribe;
import io.datcord.event.EventModuleInitializer;
import io.datcord.event.interaction.ButtonInteractionEventListener;
import io.datcord.event.interaction.registry.ButtonInteractionEventListenerRegistry;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible for handling button interaction events for the "Ticket" module in the application.
 * It implements both ButtonInteractionEventListener and EventModuleInitializer interfaces.
 *
 * On button interaction event, it logs the event's ID and edits the button with a secondary style and applies a label.
 * On initialization, it logs the initialization step and registers itself to the ButtonInteractionEventListenerRegistry with a testId.
 */
public class TicketBoardButtonInteractionEventListener implements ButtonInteractionEventListener, EventModuleInitializer {


    /**
     * Logger for this class, used to produce debug, informational, warning, and error log messages.
     */
    private static final Logger logger = LoggerFactory.getLogger(TicketBoardButtonInteractionEventListener.class);

    @Subscribe
    public void onButtonInteractionEvent(ButtonInteractionEvent event) {
        logger.debug("Received button interaction for ticket module {}",event.getIdLong());
        event.getInteraction().editButton(
                Button.secondary("other","The secondary label")
        ).queue();
    }


    @Override
    public void init() {
        logger.debug("Initializing Ticket Module");
        ButtonInteractionEventListenerRegistry.register("testId",this);
    }

}
