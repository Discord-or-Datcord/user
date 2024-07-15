package io.datcord.event.message;

import com.google.common.eventbus.Subscribe;
import io.datcord.event.message.registry.MessageReceivedEventListenerRegistry;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The MessageReceivedEventListener class is an extension of the ListenerAdapter.
 * It listens for MessageReceivedEvents and then logs the event and posts it to
 * MessageReceivedEventListenerRegistry.
 */
public class MessageReceivedEventListener extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MessageReceivedEventListener.class);

    @Override
    @Subscribe
    public void onMessageReceived(MessageReceivedEvent event) {
        logger.debug("Received message from {}", event.getAuthor().getName());
        MessageReceivedEventListenerRegistry.post(event);
    }
}
