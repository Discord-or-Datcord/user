package io.datcord.feature.jap;

import com.google.common.eventbus.Subscribe;
import io.datcord.event.EventBusProvider;
import io.datcord.event.EventSubscriber;
import io.datcord.event.message.MessageReceivedEventListener;
import io.datcord.mapper.json.JsonMapper;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

/**
 * The JiraAssigneeWebhookMessageReceivedEventListener class represents an event listener for receiving Jira assignee webhook messages.
 * It implements the MessageReceivedEventListener interface and the EventSubscriber interface.
 * When a webhook message is received, this listener checks for an assignee field in the message.
 * If the assignee field is found, it retrieves the associated Jira user ID and mentions the corresponding Discord user.
 */
public class JiraAssigneeWebhookMessageReceivedEventListener implements MessageReceivedEventListener, EventSubscriber {

    private static final Logger logger = LoggerFactory.getLogger(JiraAssigneeWebhookMessageReceivedEventListener.class);

    @Subscribe
    @Override
    public void onEvent(MessageReceivedEvent event) {
        if (event.isWebhookMessage()) {
            logger.info("Received webhook message");
            if (event.getAuthor().isBot() && event.getAuthor().getIdLong() == 1255700426435596374L || eventgetAuthor.().getIdLong() == 1262443753738731561L) { //TODO - remove this hardcoded ID for Jira webhook
                for (MessageEmbed embed : event.getMessage().getEmbeds()) {
                    for (MessageEmbed.Field field : embed.getFields()) {
                        if (Objects.requireNonNull(field.getName()).equalsIgnoreCase("Assignee")) {
                            logger.debug("Message contains Assignee {}", field.getValue());
                            JsonMapper<JiraAssociationDto> jiraAssociationDtoJsonMapper = new JiraAssociationMapper();
                            JiraAssociationDto jiraAssociationDto = jiraAssociationDtoJsonMapper.fromJson(jiraAssociationGET(field.getValue(), event.getGuild().getIdLong()));
                            event.getMessage()
                                    .reply(Objects.requireNonNull(event.getGuild().getMemberById(jiraAssociationDto.getUserId())).getAsMention())
                                    .queue();

                        }
                    }
                }
            }
        }
    }

    /**
     * Performs a GET request to the Jira association endpoint and returns the response body as a string.
     *
     * TODO: Refactor this, extract it into another class
     *
     * @param assigneeName The name of the assignee.
     * @param guildId      The ID of the guild.
     * @return The response body as a string, or null if there was no response.
     */
    private String jiraAssociationGET(String assigneeName, long guildId) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/v1/commands/jira/association"
                            + "?assigneeName=" + assigneeName.replace(" ", "%20")
                            + "&guildId=" + guildId))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Response received {}", response.body());
            return response.body();
        } catch (InterruptedException | IOException e) {
            logger.warn("No response: {}", e.getMessage());
        }
        return null;
    }

    public JiraAssigneeWebhookMessageReceivedEventListener() {
        EventBusProvider.getEventBus().register(this);
    }

}
