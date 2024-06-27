package io.datcord.command;

import com.google.common.eventbus.Subscribe;
import io.datcord.command.impl.GreetSlashCommandListener;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Dispatcher class for handling slash commands.
 *
 * Note that all commands are stored in the backend database and managed through events
 * Do not make listeners for commands that don't exist in the backend.
 */
public class SlashCommandDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(SlashCommandDispatcher.class);

    /**
     * Dispatches a received slash command to the appropriate listener.
     *
     * {@link Subscribe} is so the {@link com.google.common.eventbus.EventBus} can
     * post to this method. See {@link io.datcord.discord.event.EventDispatcher}
     *
     * TODO: Find out the difference between {@link SlashCommandInteraction} and
     * {@link net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent}
     * and if it has an impact on our application. The standard SlashCommandListener example
     * provided by JDA on GitHub uses {@link net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent},
     * however, this is currently what event is dispatched.
     *
     * @param command The slash command interaction to be dispatched.
     */
    @Subscribe
    public void dispatch(SlashCommandInteraction command) {
        logger.debug("Received Command [{}]", command.getFullCommandName());
        SlashCommandListener availableListener = commandListenerMap.get(command.getFullCommandName());
        if (availableListener != null) {
            logger.debug("Dispatching command [{}] to listener [{}]", command.getFullCommandName(), availableListener.getClass().getName());
            availableListener.onCommandReceived(command);
        } else {
            //TODO: Report back to the user that the command failed
            logger.debug("No listener found for command [{}]", command.getFullCommandName());
        }
    }

    /**
     * Registers the command listeners.
     */
    private void registerCommandListeners() {
        commandListenerMap.put("greet", new GreetSlashCommandListener());
    }

    /**
     * Constructor to initialize the dispatcher and register command listeners.
     */
    public SlashCommandDispatcher() {
        this.commandListenerMap = new ConcurrentHashMap<>();
        registerCommandListeners();
    }

    private final Map<String, SlashCommandListener> commandListenerMap;
}

