package io.datcord.event.interaction;

import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;

/**
 * An interface dedicated to handle the `EntitySelectInteractionEvent` instances.
 * Classes that are willing to process these kinds of events must implement this interface.
 * The interface includes a single method to handle the `EntitySelectInteractionEvent`.
 * <p>
 * `EntitySelectInteractionEvent` instances are generated when specific interactions occur in Discord servers.
 * These instances carry relevant information such as what selection has been taken by users, and the context of the selection.
 * For example - the selected option in a dropdown menu in the Discord chat.
 * Implement classes can use this information to create specific interactive experiences based on the selections made by the users.
 */
public interface EntitySelectInteractionEventListener {

    /**
     * React to an EntitySelectInteractionEvent.
     *
     * @param event The EntitySelectInteractionEvent that will be processed
     */
    void onEntitySelectInteraction(EntitySelectInteractionEvent event);
}
