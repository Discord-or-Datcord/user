package io.datcord.command.jira;

import com.google.common.eventbus.Subscribe;
import io.datcord.event.EventBusProvider;
import io.datcord.event.EventSubscriber;
import io.datcord.event.interaction.EntitySelectInteractionEventListener;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JiraSlashCommandEntitySelectionInteractionEventListener implements EntitySelectInteractionEventListener, EventSubscriber {

    private static final Logger logger = LoggerFactory.getLogger(JiraSlashCommandEntitySelectionInteractionEventListener.class);

    @Subscribe
    @Override
    public void onEvent(EntitySelectInteractionEvent event) {
        logger.info("Received event {}", event.getSelectMenu().getId());
    }

    public JiraSlashCommandEntitySelectionInteractionEventListener() {
        EventBusProvider.getEventBus().register(this);
    }
}
