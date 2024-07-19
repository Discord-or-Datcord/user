import io.datcord.command.CommandModuleInitializer;
import io.datcord.event.EventSubscriber;

module jira {
    requires commands;
    requires net.dv8tion.jda;
    requires org.slf4j;
    requires events;
    requires com.google.common;

    exports io.datcord.command.jira;

    provides CommandModuleInitializer
            with io.datcord.command.jira.JiraSlashCommandListener;
    provides EventSubscriber
            with io.datcord.command.jira.JiraSlashCommandListener,
                    io.datcord.command.jira.JiraSlashCommandEntitySelectionInteractionEventListener;
}