package io.datcord.event;

import com.google.common.eventbus.EventBus;
import io.datcord.event.session.ReadyEventListener;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventDispatcher extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(EventDispatcher.class);

    @Override
    public void onGenericEvent(GenericEvent event) {
        logger.debug("Received event [{}]",event.toString());
        eventBus.post(event);
    }

    private void registerEventListeners() {
        eventBus.register(new ReadyEventListener());
//        eventBus.register(new SlashCommandDispatcher());
    }

    public EventDispatcher(EventBus eventBus) {
        this.eventBus = eventBus;

        registerEventListeners();
    }
    private final EventBus eventBus;
}
