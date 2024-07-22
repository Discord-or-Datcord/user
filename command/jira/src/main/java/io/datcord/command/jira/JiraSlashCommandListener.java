package io.datcord.command.jira;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.eventbus.Subscribe;
import io.datcord.command.CommandModuleInitializer;
import io.datcord.command.SlashCommandListener;
import io.datcord.command.SlashCommandListenerRegistry;
import io.datcord.event.EventBusProvider;
import io.datcord.event.EventSubscriber;
import io.datcord.event.interaction.command.CommandAutoCompleteInteractionEventListener;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Set;

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

        event.reply("Select user to map to assignee")
                .addActionRow(EntitySelectMenu.create("jira_user_assignee_mapping", EntitySelectMenu.SelectTarget.USER).build())
                .setEphemeral(true)
                .queue();

        ResponseBuilder.mapStringField("assigneeName",assignee);
        ResponseBuilder.mapLongField("guildId",event.getGuild().getIdLong());


        logger.info("Received options action [{}] assignee [{}]", action, assignee);
    }

    @Override
    @Subscribe
    public void onEvent(CommandAutoCompleteInteractionEvent event) {
        logger.info("Jira command auto-complete interaction");
        final Set<Command.Choice> choices = new HashSet<>();
        logger.debug("Focused option {} ", event.getFocusedOption().getName());
        if (event.getFocusedOption().getName().equals("action")) {
            choices.add(new Command.Choice("create", "create"));
        }

        event.replyChoices(choices).submit();
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

    public JiraSlashCommandListener() {
        EventBusProvider.getEventBus().register(this);
    }

}
