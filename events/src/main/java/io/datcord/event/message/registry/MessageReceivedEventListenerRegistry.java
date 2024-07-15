package io.datcord.event.message.registry;

import io.datcord.event.EventModuleInitializer;
import io.datcord.event.interaction.ButtonInteractionEventListener;
import io.datcord.event.message.MessageReceivedEventListener;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;


/**
 * This class is a registry for message received event listeners.
 * <p>
 * Any event listener that wants to receive and process {@link MessageReceivedEvent} should be
 * registered in this class using the {@link #register(MessageReceivedEventListener)} method.
 * </p>
 * <p>
 * The {@link #post(MessageReceivedEvent)} method should be called whenever a {@link MessageReceivedEvent} is received,
 * so that all registered listeners can be notified and can handle the event.
 * </p>
 * <p>
 * Listeners can also be unregistered using the {@link #unregister(MessageReceivedEventListener)} method.
 * </p>
 * <p>
 * This class also initializes event modules by loading implementations of {@link EventModuleInitializer}.
 * This is done in the static block to ensure that the event modules are initialized at class loading time.
 * </p>
 */
public class MessageReceivedEventListenerRegistry {

    private static final List<MessageReceivedEventListener> EVENT_LISTENERS = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(MessageReceivedEventListenerRegistry.class);

    static {
        initializeModules();
    }


    /**
     * Registers a given {@link MessageReceivedEventListener} to the list of event listeners.
     * Once registered, the listener will be notified of any {@link MessageReceivedEvent} that are posted to
     * the registry, allowing it to act upon those events.
     *
     * @param listener the {@link MessageReceivedEventListener} to be registered
     */
    public static void register(MessageReceivedEventListener listener) {
            EVENT_LISTENERS.add(listener);
            logger.debug("Registered MessageReceivedEventListener [{}]", listener);

    }


    /**
     * Unregisters a given {@link MessageReceivedEventListener} from the list of event listeners.
     * Once unregistered, the listener will no longer receive notifications about {@link MessageReceivedEvent} posted to the registry.
     *
     * @param listener the {@link MessageReceivedEventListener} to be unregistered
     */
    public static void unregister(MessageReceivedEventListener listener) {
        EVENT_LISTENERS.remove(listener);
        logger.debug("Unregistered MessageReceivedEventListener [{}]", listener);
    }


    /**
     * Posts a given {@link MessageReceivedEvent} to all registered event listeners.
     * Each listener handles the event in its own way as defined by its implementation.
     *
     * @param event the {@link MessageReceivedEvent} that should be posted to the listeners
     */
    public static void post(MessageReceivedEvent event) {
        logger.debug("Received MessageReceivedEvent Message ID:[{}]", event.getMessageId());
        EVENT_LISTENERS.forEach(messageReceivedEventListener -> messageReceivedEventListener.onMessageReceived(event));
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
