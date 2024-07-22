package io.datcord.command.jira;

import com.google.common.eventbus.Subscribe;
import io.datcord.event.EventBusProvider;
import io.datcord.event.EventSubscriber;
import io.datcord.event.interaction.EntitySelectInteractionEventListener;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class JiraSlashCommandEntitySelectionInteractionEventListener implements EntitySelectInteractionEventListener, EventSubscriber {

    private static final Logger logger = LoggerFactory.getLogger(JiraSlashCommandEntitySelectionInteractionEventListener.class);

    @Subscribe
    @Override
    public void onEvent(EntitySelectInteractionEvent event) {
        logger.info("Received Jira Entity select event {}", event.getSelectMenu().getId());
        if (Objects.requireNonNull(event.getSelectMenu().getId()).equals("jira_user_assignee_mapping")) {
            ResponseBuilder.mapLongField("userId",event.getInteraction().getUser().getIdLong());

            logger.info("Response {}",ResponseBuilder.asJsonString());

            ResponseBuilder.jiraAssociationPOST();
        }
    }

    public JiraSlashCommandEntitySelectionInteractionEventListener() {
        EventBusProvider.getEventBus().register(this);
    }
}
