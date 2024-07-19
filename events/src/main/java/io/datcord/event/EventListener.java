package io.datcord.event;

import net.dv8tion.jda.api.events.GenericEvent;

/**
 * The EventListener interface represents a listener for generic events.
 *
 * @param <E> The type of event.
 */
public interface EventListener<E extends GenericEvent> {

    /**
     * This method is called when an event occurs.
     *
     * @param event The event that occurred.
     */
    void onEvent(E event);
}
