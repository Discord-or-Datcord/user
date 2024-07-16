import io.datcord.event.EventModuleInitializer;

module events {
    uses EventModuleInitializer;
    requires com.google.common;
    requires net.dv8tion.jda;
    requires org.slf4j;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;
    requires util;
    exports io.datcord.event;
    exports io.datcord.event.interaction;
    exports io.datcord.event.session;
    exports io.datcord.event.interaction.registry;
    exports io.datcord.event.interaction.dispatcher;
    exports io.datcord.event.message;
}