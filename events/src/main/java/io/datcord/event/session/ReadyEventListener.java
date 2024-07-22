package io.datcord.event.session;

import com.google.common.eventbus.Subscribe;
import io.datcord.api.service.command.CommandService;
import io.datcord.api.service.command.CommandServiceImpl;
import io.datcord.mapper.json.JsonMapper;
import io.datcord.mapper.json.impl.SlashCommandJsonMapper;
import io.datcord.util.ApplicationConfigUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Listener class that handles the bot {@link ReadyEvent} and updates global command and guild commands.
 *
 * Deprecated - Will be migrating to new event system. Code won't change much internally
 */
@Deprecated(forRemoval = true)
public class ReadyEventListener extends ListenerAdapter {

    /**
     * Logger instance for logging information.
     */
    private static final Logger logger = LoggerFactory.getLogger(ReadyEventListener.class);

    /**
     * Called when the bot is ready.
     *
     * @param event the ReadyEvent
     */
    @Override
    @Subscribe
    public void onReady(ReadyEvent event) {

        JDA jda = event.getJDA();

        //This is here to prevent rate limit issues if the bot needs to be restarted multiple times in quick succession
        //Set this property to false if you are not working on commands actively
        if (ApplicationConfigUtils.getBooleanProperty("io.datcord.application.enable_command_loading")) {
            loadCommands(jda);
        }
    }

    private void loadCommands(JDA jda) {
        /*
         * Update global commands.
         */
        jda.updateCommands().addCommands(readCommands()).queue();

        logger.debug("Loaded global commands");

        /*
         * Update commands for each guild.
         */
        jda.getGuilds().forEach(guild -> {
            logger.debug("Loading commands for guild {}", guild.getName());
            guild.updateCommands().addCommands(readCommands(guild.getIdLong())).queue();
            logger.debug("Loaded commands for guild {}", guild.getIdLong());
            logger.debug("Loaded features for guild {}", guild.getName());
            //TODO: Add feature loading
        });

        logger.debug("All commands loaded");
    }

    /**
     * Reads command data from the command service and returns it as a collection of CommandData objects.
     *
     * @return a collection of CommandData objects representing the commands
     */
    private Collection<CommandData> readCommands() {
        CommandService commandService = new CommandServiceImpl();
        String response = commandService.requestGlobalCommandData();

        return getCommandData(response);
    }

    /**
     * Reads command data from the command service and returns it as a collection of CommandData objects.
     *
     * @param guildId the ID of the guild
     * @return a collection of CommandData objects representing the commands
     */
    private Collection<CommandData> readCommands(long guildId) {
        CommandService commandService = new CommandServiceImpl();
        String response = commandService.requestGuildCommandData(guildId);

        return getCommandData(response);
    }

    /**
     * Retrieves a collection of CommandData objects from the provided response string.
     *
     * @param response the response string containing the command data
     * @return a collection of CommandData objects representing the commands
     */
    private Collection<CommandData> getCommandData(String response) {
        JsonMapper<CommandData> commandDataJsonMapper = new SlashCommandJsonMapper();

        return commandDataJsonMapper.fromJsonAsCollection(response);
    }

}

