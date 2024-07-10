package io.datcord.event.interaction.registry;

import io.datcord.event.EventModuleInitializer;
import io.datcord.event.interaction.ButtonInteractionEventListener;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registry for managing button interaction event listeners.
 */
public class ButtonInteractionEventListenerRegistry {

    private static final Map<String, ButtonInteractionEventListener> EVENT_LISTENER_MAP = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(ButtonInteractionEventListenerRegistry.class);

    static {
        initializeModules();
    }

    /**
     * Registers a button interaction event listener with a given button ID.
     *
     * @param buttonId The ID of the button.
     * @param listener The listener to handle the button interaction event.
     */
    public static void register(String buttonId, ButtonInteractionEventListener listener) {
        if (!EVENT_LISTENER_MAP.containsKey(buttonId)) {
            EVENT_LISTENER_MAP.put(buttonId, listener);
            logger.debug("Registered button with Id [{}]", buttonId);
        } else {
            logger.warn("Button with Id [{}] already registered", buttonId);
        }
    }

    /**
     * Unregisters a button interaction event listener with a given button ID.
     *
     * @param buttonId The ID of the button.
     */
    public static void unregister(String buttonId) {
        if (EVENT_LISTENER_MAP.containsKey(buttonId)) {
            EVENT_LISTENER_MAP.remove(buttonId);
            logger.debug("Unregistered button with Id [{}]", buttonId);
        } else {
            logger.warn("Button with Id [{}] not registered", buttonId);
        }
    }

    /**
     * Posts a button interaction event to the appropriate listener.
     *
     * @param event The button interaction event to post.
     */
    public static void post(ButtonInteractionEvent event) {
        logger.debug("Received ButtonInteractionEvent Button ID:[{}]", event.getButton().getId());
        final String buttonId = event.getButton().getId();
        ButtonInteractionEventListener availableListener = EVENT_LISTENER_MAP.get(buttonId);
        if (availableListener != null) {
            logger.debug("Dispatching ButtonInteractionEvent [{}] to listener [{}]", buttonId, availableListener.getClass().getName());
            availableListener.onButtonInteractionEvent(event);
        } else {
            // TODO: Report back to the user that the event failed
            logger.debug("No listener found for button [{}]", buttonId);
        }
    }

    /**
     * Initializes event modules by loading implementations of {@link EventModuleInitializer}.
     */
    private static void initializeModules() {
        logger.debug("Initializing event modules");
        ServiceLoader<EventModuleInitializer> serviceLoader = ServiceLoader.load(EventModuleInitializer.class);
        for (EventModuleInitializer initializer : serviceLoader) {
            logger.debug("Initializing module {}", initializer);
            initializer.init();
        }
    }
}
