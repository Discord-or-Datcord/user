package io.datcord.command.jira;

import com.google.common.eventbus.Subscribe;
import io.datcord.command.CommandModuleInitializer;
import io.datcord.command.SlashCommandListener;
import io.datcord.command.SlashCommandListenerRegistry;
import io.datcord.event.EventBusProvider;
import io.datcord.event.EventSubscriber;
import io.datcord.event.interaction.command.CommandAutoCompleteInteractionEventListener;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JiraSlashCommandListener implements CommandAutoCompleteInteractionEventListener, SlashCommandListener, CommandModuleInitializer, EventSubscriber {

    private static final Logger logger = LoggerFactory.getLogger(JiraSlashCommandListener.class);

    @Override
    public void initCommand() {
        SlashCommandListenerRegistry.register("jira", this);
    }

    @Override
    public void onCommandReceived(SlashCommandInteractionEvent event) {
        logger.info("Jira command received {}", event.getCommandString());

        String action = (String) getOptionValue(event, "action");
        String assignee = (String) getOptionValue(event, "assignee");
    }

    private Object getOptionValue(SlashCommandInteractionEvent event, String optionName) {
        OptionMapping option = event.getOption(optionName);
        if (option != null) {
            String optionValue = option.getAsString();
            logger.info("Option {} has value {}", optionName, optionValue);
            return optionValue;
        } else {
            logger.error("Option {} is not found in events.", optionName);
            throw new RuntimeException("Option " + optionName + " is not found in events.");
        }
    }

    @Override
    @Subscribe
    public void onEvent(CommandAutoCompleteInteractionEvent event) {
        logger.info("Jira command auto-complete interaction");
    }

    public JiraSlashCommandListener() {
        EventBusProvider.getEventBus().register(this);
    }

}
