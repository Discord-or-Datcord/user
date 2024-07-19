package io.datcord.event.interaction.command;

import io.datcord.event.EventListener;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;

/**
 * Interface for an event listener that handles a command auto-complete interaction event.
 */
public interface CommandAutoCompleteInteractionEventListener extends EventListener<CommandAutoCompleteInteractionEvent> {

    /**
     * Handles a command auto-complete interaction event.
     *
     * @param event The command auto-complete interaction event to be handled.
     */
    void onEvent(CommandAutoCompleteInteractionEvent event);
}
