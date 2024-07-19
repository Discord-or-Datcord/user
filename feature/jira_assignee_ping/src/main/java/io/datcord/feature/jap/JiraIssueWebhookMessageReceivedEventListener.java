package io.datcord.feature.jap;

import io.datcord.event.EventSubscriber;
import io.datcord.event.message.MessageReceivedEventListener;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JiraIssueWebhookMessageReceivedEventListener implements MessageReceivedEventListener, EventSubscriber {

    private static final Logger logger = LoggerFactory.getLogger(JiraIssueWebhookMessageReceivedEventListener.class);


    @Override
    public void onEvent(MessageReceivedEvent event) {
        logger.info("Received Jira new issue message");
    }
}
