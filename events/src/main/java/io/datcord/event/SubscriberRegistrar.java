package io.datcord.event;

import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;


/**
 * The SubscriberRegistrar class is responsible for registering event subscribers to an EventBus.
 * It uses a ServiceLoader to load instances of EventSubscriber from the classpath and registers them with the EventBus.
 */
public class SubscriberRegistrar {

    private static final Logger logger = LoggerFactory.getLogger(SubscriberRegistrar.class);

    /**
     * Registers event subscribers to the given EventBus using a ServiceLoader to load instances of EventSubscriber
     * from the classpath.
     *
     * @param eventBus the EventBus to register the subscribers with
     */
    public static void registerSubscribers(EventBus eventBus) {
        logger.info("Registering subscribers...");
        ServiceLoader<EventSubscriber> serviceLoader = ServiceLoader.load(EventSubscriber.class);
        for (EventSubscriber subscriber : serviceLoader) {
            logger.info("Registering subscriber {}", subscriber.getClass().getName());
            eventBus.register(subscriber);
        }
    }
}

