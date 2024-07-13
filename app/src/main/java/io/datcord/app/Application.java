package io.datcord.app;

import com.google.common.eventbus.EventBus;
import io.datcord.command.SlashCommandDispatcher;
import io.datcord.event.EventDispatcher;
import io.datcord.event.interaction.dispatcher.ButtonInteractionEventDispatcher;
import io.datcord.event.interaction.dispatcher.EntitySelectInteractionEventDispatcher;
import io.datcord.event.message.MessageReceivedEventListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

/**
 * The main application class for the Datcord User bot.
 */
public class Application {

    /**
     * Logger instance for logging information.
     */
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    /**
     * The main method to start the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // Log the start of the application
        logger.info("Starting Datcord User");

        EventBus eventBus = new EventBus();

        /**
         * Create a new JDABuilder with the bot token from environment variables
         * and add event listeners for handling ready and slash command events.
         *
         * TODO: Change event listeners to a single event dispatcher
         */
        JDABuilder.createDefault(System.getenv("BOT_TOKEN"), Collections.emptyList())
                .enableIntents(
                        GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS)
                )
                .addEventListeners(
                        new EventDispatcher(eventBus),
                        new SlashCommandDispatcher(),
                        new ButtonInteractionEventDispatcher(),
                        new EntitySelectInteractionEventDispatcher()
                )
                .build();
    }


}
