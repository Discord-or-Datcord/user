package io.datcord.feature.jap;

import com.google.common.eventbus.Subscribe;
import io.datcord.event.EventBusProvider;
import io.datcord.event.EventSubscriber;
import io.datcord.event.message.MessageReceivedEventListener;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;

public class JiraAssigneeWebhookMessageReceivedEventListener implements MessageReceivedEventListener, EventSubscriber {

    private static final Logger logger = LoggerFactory.getLogger(JiraAssigneeWebhookMessageReceivedEventListener.class);

    @Subscribe
    @Override
    public void onEvent(MessageReceivedEvent event) {
        if (event.isWebhookMessage()) {
            for (MessageEmbed embed : event.getMessage().getEmbeds()) {
                for (MessageEmbed.Field field : embed.getFields()) {
                    if (Objects.requireNonNull(field.getName()).equalsIgnoreCase("Assignee")) {
                        logger.debug("Assignee {}",field.getValue());

                    }
                }
            }
        }
    }

    private long getUserId(String assigneeName) {
        Map<String,Long> assigneeMap = Map.of(
                "Quinton Kushner", 265025998582579211L,
                "asaad abu elhija",979618581060915251L,
                "M Th",1176167264769286265L,
                "Tim Ayers | Dev Xenon",401203196866854925L
        );
        return assigneeMap.get(assigneeName);
    }

    public JiraAssigneeWebhookMessageReceivedEventListener() {
        EventBusProvider.getEventBus().register(this);
    }

}
