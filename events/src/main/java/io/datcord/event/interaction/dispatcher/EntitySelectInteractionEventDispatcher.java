package io.datcord.event.interaction.dispatcher;

import io.datcord.event.interaction.registry.EntitySelectInteractionEventListenerRegistry;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The EntitySelectInteractionEventDispatcher is a class which extends the ListenerAdapter class.
 * An instance of this class can listen for EntitySelectInteractionEvent and react accordingly.
 * When an EntitySelectInteractionEvent is fired, its overridden method onEntitySelectInteraction
 * will be called. This method processes the provided EntitySelectInteractionEvent, logs the event ID,
 * and dispatches the event to the registered event listeners.
 *
 * This class also contains a Logger used for logging information about EntitySelectInteractionEvent.
 * The Logger is statically initialized with the class' canonical name.
 *
 * @see net.dv8tion.jda.api.hooks.ListenerAdapter
 * @see net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent
 * @see io.datcord.event.interaction.registry.EntitySelectInteractionEventListenerRegistry
 */
public class EntitySelectInteractionEventDispatcher extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(EntitySelectInteractionEventDispatcher.class);


    /**
     * This method is an overridden version of onEntitySelectInteraction from ListenerAdapter.
     * When an EntitySelectInteractionEvent occurs, this method is triggered.
     * It first logs the event ID and then dispatches the event to the registered event listeners.
     *
     * @param event The EntitySelectInteractionEvent that has occurred
     */
    @Override
    public void onEntitySelectInteraction(EntitySelectInteractionEvent event) {
        logger.info("Received EntitySelectInteractionEvent event ID:[{}] interaction ID:[{}]", event.getIdLong(), event.getInteraction().getIdLong());
        EntitySelectInteractionEventListenerRegistry.post(event);
    }
}
