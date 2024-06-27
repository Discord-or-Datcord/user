package io.datcord;

import io.datcord.discord.event.interaction.SlashCommandListener;
import io.datcord.discord.event.session.ReadyEventListener;
import net.dv8tion.jda.api.JDABuilder;
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

        /**
         * Create a new JDABuilder with the bot token from environment variables
         * and add event listeners for handling ready and slash command events.
         *
         * TODO: Change event listeners to a single event dispatcher
         */
        JDABuilder.createLight(System.getenv("BOT_TOKEN"), Collections.emptyList())
                .addEventListeners(
                        new ReadyEventListener(),
                        new SlashCommandListener()
                )
                .build();
    }
}
