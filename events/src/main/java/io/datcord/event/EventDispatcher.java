package io.datcord.event;

import com.google.common.eventbus.EventBus;
import io.datcord.event.session.ReadyEventListener;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dispatches Discord events using an {@link EventBus}
 *
 * Register the listener and ensure {@link com.google.common.eventbus.Subscribe}
 * is annotation on the receiving method
 */
public class EventDispatcher extends ListenerAdapter {

    /**
     * Logger for the class
     */
    private static final Logger logger = LoggerFactory.getLogger(EventDispatcher.class);

    /**
     * This method is called when the {@link GenericEvent} event is
     * dispatched on Discord. {@link GenericEvent} is the parent class
     * of all event objects
     * @param event the event received
     */
    @Override
    public void onGenericEvent(GenericEvent event) {
        logger.debug("Received event [{}]",event.toString());
        eventBus.post(event);
    }

    /**
     * Register event listeners to the {@link EventBus}
     */
    private void registerEventListeners() {
        eventBus.register(new ReadyEventListener());
        SubscriberRegistrar.registerSubscribers(eventBus);
    }

    public EventDispatcher(EventBus eventBus) {
        this.eventBus = eventBus;

        registerEventListeners();
    }
    private final EventBus eventBus;
}
