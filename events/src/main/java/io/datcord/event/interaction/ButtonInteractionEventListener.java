package io.datcord.event.interaction;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

/**
 * This is an interface for handling button interaction events.
 *
 * It defines a method, 'onButtonInteractionEvent', to be implemented by any class that
 * handles the specific button interaction event from Discord's JDA API.
 * "ButtonInteractionEvent" is a class from JDA representing a button click by a user in discord.
 */
public interface ButtonInteractionEventListener {

    /**
     * Triggered when a user interacts with a button on Discord.
     *
     * @param event The event triggered by a button interaction on Discord.
     */
    void onButtonInteractionEvent(ButtonInteractionEvent event);
}
