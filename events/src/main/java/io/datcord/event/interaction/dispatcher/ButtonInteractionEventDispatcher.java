package io.datcord.event.interaction.dispatcher;

import io.datcord.event.interaction.registry.ButtonInteractionEventListenerRegistry;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dispatcher class for handling button interaction events.
 */
public class ButtonInteractionEventDispatcher extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ButtonInteractionEventDispatcher.class);

    /**
     * Handles button interaction events by logging the event and posting it to the registry.
     *
     * @param event The button interaction event to handle.
     */
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        logger.info("Received ButtonInteractionEvent Interaction ID:[{}] Button ID:[{}]", event.getIdLong(), event.getButton().getId());
        ButtonInteractionEventListenerRegistry.post(event);
    }
}
