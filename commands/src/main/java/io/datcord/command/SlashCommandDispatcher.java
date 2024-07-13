package io.datcord.command;

import io.datcord.command.impl.GreetSlashCommandListener;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Dispatcher class for handling slash commands.
 *
 * <p>Note that all commands are stored in the backend database and managed through events.
 * Do not make listeners for commands that don't exist in the backend.</p>
 */
public class SlashCommandDispatcher extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SlashCommandDispatcher.class);

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent command) {
        SlashCommandListenerRegistry.post(command);
    }

    /**
     * Registers the global command listeners.
     */
    private void registerCommandListeners() {
        SlashCommandListenerRegistry.register("greet", new GreetSlashCommandListener());
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

