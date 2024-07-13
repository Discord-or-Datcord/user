package io.datcord.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registry for managing slash command listeners.
 */
public class SlashCommandListenerRegistry {

    private static final Map<String, SlashCommandListener> EVENT_LISTENER_MAP = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(SlashCommandListenerRegistry.class);

    static {
        initializeModules();
    }

    /**
     * Registers a slash command listener with a given identifier.
     *
     * @param identifier The identifier for the command.
     * @param listener The listener to handle the command.
     */
    public static void register(String identifier, SlashCommandListener listener) {
        if (!EVENT_LISTENER_MAP.containsKey(identifier)) {
            EVENT_LISTENER_MAP.put(identifier, listener);
            logger.debug("Registered command with identifier [{}]", identifier);
        } else {
            logger.warn("Command with identifier [{}] already registered", identifier);
        }
    }

    /**
     * Unregisters a slash command listener with a given identifier.
     *
     * @param identifier The identifier for the command.
     */
    public static void unregister(String identifier) {
        if (EVENT_LISTENER_MAP.containsKey(identifier)) {
            EVENT_LISTENER_MAP.remove(identifier);
            logger.debug("Unregistered command with identifier [{}]", identifier);
        } else {
            logger.warn("Command with identifier [{}] not registered", identifier);
        }
    }

    /**
     * Posts a slash command interaction event to the appropriate listener.
     *
     * @param event The slash command interaction event to post.
     */
    public static void post(SlashCommandInteractionEvent event) {
        logger.debug("Received SlashCommandInteractionEvent Name:[{}]", event.getName());
        final String identifier = event.getName();
        SlashCommandListener availableListener = EVENT_LISTENER_MAP.get(identifier);
        if (availableListener != null) {
            logger.debug("Dispatching SlashCommandInteractionEvent [{}] to listener [{}]", identifier, availableListener.getClass().getName());
            availableListener.onCommandReceived(event);
        } else {
            // TODO: Report back to the user that the event failed
            logger.debug("No listener found for command [{}]", identifier);
        }
    }

    /**
     * Initializes command modules by loading implementations of {@link CommandModuleInitializer}.
     */
    private static void initializeModules() {
        logger.debug("Initializing command modules");
        ServiceLoader<CommandModuleInitializer> serviceLoader = ServiceLoader.load(CommandModuleInitializer.class);
        for (CommandModuleInitializer initializer : serviceLoader) {
            logger.debug("Initializing module {}", initializer);
            initializer.initCommand();
        }
    }
}

