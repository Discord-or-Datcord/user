package io.datcord.event.message;

import io.datcord.event.EventListener;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;


/** 
 * listens for MessageDeleteEvent.
 */


public interface MessageDeleteEventListener extends EventListener<MessageDeleteEvent> {

/**
 *Can be used to detect when a Message is deleted in a private or public MessageChannel. 
 */

    void onEvent(MessageDeleteEvent event);
}