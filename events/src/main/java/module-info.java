
module events {
    uses io.datcord.event.EventSubscriber;
    requires com.google.common;
    requires net.dv8tion.jda;
    requires org.slf4j;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;
    requires util;
    requires static lombok;
    exports io.datcord.event;
    exports io.datcord.event.interaction;
    exports io.datcord.event.session;
    exports io.datcord.event.message;
    exports io.datcord.event.interaction.command;
    exports io.datcord.mapper.json;
}