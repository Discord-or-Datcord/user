package io.datcord.event.message;

import io.datcord.event.EventListener;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * The MessageEventListener interface represents an event listener for receiving messages.
 * Implementing classes can register to receive message events and perform certain actions
 * when a message is received.
 *
 * <p>
 * Example usage:
 * <pre>{@code
 * // Create an instance of a class that implements MessageEventListener
 * MessageEventListener listener = new MyMessageEventListener();
 *
 * // Register the listener to listen for message events
 * MessageEventBus.registerListener(listener);
 * }</pre>
 *
 * <p>
 * Implementing classes must implement the {@link #onEvent(MessageReceivedEvent)} (MessageReceivedEvent)} method,
 * which is called when a message is received.
 */
public interface MessageReceivedEventListener extends EventListener<MessageReceivedEvent> {


    /**
     * This method is called when a message is received.
     *
     * @param event the event object representing the received message
     */
    void onEvent(MessageReceivedEvent event);
}
