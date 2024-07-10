package io.datcord.event.interaction.registry;

import io.datcord.event.EventModuleInitializer;
import io.datcord.event.interaction.EntitySelectInteractionEventListener;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

public class EntitySelectInteractionEventListenerRegistry {


    /**
     * A ConcurrentHashMap that holds entity IDs as keys and their corresponding listeners as values.
     */
    private static final Map<String, EntitySelectInteractionEventListener> EVENT_LISTENER_MAP = new ConcurrentHashMap<>();

    /**
     * Logger instance used to log messages for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(EntitySelectInteractionEventListenerRegistry.class);

    /**
     * This static block gets executed when the class is loaded. It calls the initializeModules() method
     * to initialize all event modules.
     *
     * TODO: Because modules are initialized here they are initialized twice once for loading button interactions
     * and once for loading entity select interactions. This results in an attempt to load both modules twice
     * this may need to be fixed by changing the logic of when modules are loaded such as moving it to {@link Application}
     * or a lock may need to be created to ensure modules are loaded only once
     */
    static {
        initializeModules();
    }

    /**
     * This method registers an EntitySelectInteractionEventListener against the entity ID provided.
     * If the entity ID is already registered, the method logs a warning and doesn't override
     * the existing listener.
     *
     * @param entityId An Entity ID as a {@link String}.
     * @param listener An implementation of {@link EntitySelectInteractionEventListener}
     *                 that should be fired when the event associated with this entity ID is triggered.
     */
    public static void register(String entityId, EntitySelectInteractionEventListener listener) {
        if (!EVENT_LISTENER_MAP.containsKey(entityId)) {
            EVENT_LISTENER_MAP.put(entityId, listener);
            logger.debug("Registered entity with Id [{}]", entityId);
        } else {
            logger.warn("Entity with Id [{}] already registered", entityId);
        }
    }

    /**
     * This method unregisters a listener linked to the provided entity ID.
     * If the entity ID isn't registered previously, then it simply logs a warning message.
     *
     * @param entityId An Entity ID as a {@link String}.
     */
    public static void unregister(String entityId) {
        if (EVENT_LISTENER_MAP.containsKey(entityId)) {
            EVENT_LISTENER_MAP.remove(entityId);
            logger.debug("Unregistered entity with Id [{}]", entityId);
        } else {
            logger.warn("Entity with Id [{}] not registered", entityId);
        }
    }


    /**
     * Posts (dispatches) an `EntitySelectInteractionEvent` to the appropriate registered listener, if any.
     * The event gets processed by the listener if it is present in the `EVENT_LISTENER_MAP`, otherwise, it just logs that no listener was found.
     *
     * @param event The `EntitySelectInteractionEvent` that will be dispatched to the appropriate listener
     */
    public static void post(EntitySelectInteractionEvent event) {
        logger.debug("Received EntitySelectInteractionEvent ID:[{}] interaction ID {}", event.getId(), event.getSelectMenu().getId());
        final String entityId = event.getSelectMenu().getId();
        EntitySelectInteractionEventListener availableListener = EVENT_LISTENER_MAP.get(entityId);
        if (availableListener != null) {
            logger.debug("Dispatching EntitySelectInteractionEvent [{}] to listener [{}]", entityId, availableListener.getClass().getName());
            availableListener.onEntitySelectInteraction(event);
        } else {
            logger.debug("No listener found for event [{}] menu_id [{}]", event.getId(), entityId);
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
