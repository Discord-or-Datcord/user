import io.datcord.event.EventSubscriber;

module jira.assignee.ping {
    requires events;
    requires net.dv8tion.jda;
    requires org.slf4j;
    requires com.google.common;

    exports io.datcord.feature.jap;

    provides EventSubscriber
            with io.datcord.feature.jap.JiraAssigneeWebhookMessageReceivedEventListener,
                    io.datcord.feature.jap.JiraIssueWebhookMessageReceivedEventListener;
}