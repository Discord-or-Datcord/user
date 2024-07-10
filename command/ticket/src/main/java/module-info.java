import io.datcord.command.CommandModuleInitializer;
import io.datcord.event.EventModuleInitializer;

/**
 * Module definition for the ticket module.
 *
 * This module handles ticket-related commands and events.
 * It requires several other modules and provides specific implementations
 * for event and command initializers.
 */
module ticket {
    requires events;
    requires net.dv8tion.jda;
    requires com.google.common;
    requires org.slf4j;
    requires commands;

    provides EventModuleInitializer
            with io.datcord.command.ticket.TicketBoardButtonInteractionEventListener,io.datcord.command.ticket.TicketBoardEntitySelectInteractionEventListener;
    provides CommandModuleInitializer
            with io.datcord.command.ticket.TicketSlashCommandListener;

    exports io.datcord.command.ticket;
}
